/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.archivcore.logon.auth;

import io.archivcore.account.Account;
import io.archivcore.logon.LogonSession;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
import io.archivcore.client.Client;
import io.archivcore.client.ClientInfo;
import io.archivcore.logon.LogonServer;
import io.archivcore.networking.packet.Packet;
import io.archivcore.realm.Realm;
import io.archivcore.realm.Realmlist;
import io.archivcore.utils.BigNumber;
import io.archivcore.utils.HashUtils;

/**
 *
 * @author Dusko
 */
public class AuthHandler {

    public void handlePacket(LogonSession session, Packet packet) {
        AuthOpcode opcode = AuthOpcode.getOpcode(packet.rawOpcode);
        switch (opcode) {
            case ClientLogonChallenge: clientLogonChallengeHandler(session, packet); break;
            case ClientLogonProof:     clientLogonProofHandler(session, packet);     break;
            case ClientRealmlist:      clientRealmlistHandler(session, packet);      break;
        }
    }

    // BigNumbers for the SRP6 implementation
    private static final BigNumber N = new BigNumber("894B645E89E1535BBDAD5B8B290650530801B18EBFBF5E8FAB3C82872A3E9BB7", 16); // Safe prime
    private static final BigNumber g = new BigNumber("7"); // Generator
    private static final BigNumber s = new BigNumber().setRand(32); // Salt
    private static final BigNumber k = new BigNumber("3"); // k - used to generate a lot..
    private static final BigNumber b = new BigNumber().setRand(19); // server private value

    private BigNumber v; // Verifier
    private BigNumber gmod; // gmod - used to calculate B
    private BigNumber B; // server public value

    private final MessageDigest digest = HashUtils.getSHA1();
    private Account account;

    private String parseUsername(Packet packet) {
        byte length = packet.body.getByte();

        byte[] usernameBytes = new byte[length];
        packet.body.getBytes(usernameBytes);

        return new String(usernameBytes);
    }

    private void clientLogonChallengeHandler(LogonSession session, Packet packet) {
        ClientInfo clientInfo = ClientInfo.parse(packet);
        String username = parseUsername(packet);
        account = Account.findBy(username);

        if(account == null) {
            AuthPacket response = new AuthPacket(AuthOpcode.ClientLogonChallenge.getRawValue(), 3);
            response.body.putByte((byte)0);
            response.body.putByte(AuthCodes.AuthUnknownAccount.rawValue);
            session.send(response);

            return;
        }

        if (LogonServer.getInstance().getClient(username) != null) {
            AuthPacket response = new AuthPacket(AuthOpcode.ClientLogonChallenge.getRawValue(), 3);
            response.body.putByte((byte)0);
            response.body.putByte(AuthCodes.AuthAlreadyLoggedIn.rawValue);
            session.send(response);

            return;
        }

        session.client = new Client(username, clientInfo);

        // Generate x - the Private key
        digest.update(s.asByteArray(32));
        digest.update(account.getHashBytes());

        BigNumber x = new BigNumber();
        x.setBinary(digest.digest());

        // Generate B - the server public value
        v = g.modPow(x, N);
        gmod = g.modPow(b, N);
        B = (v.multiply(k).add(gmod)).remainder(N);

        AuthPacket response = new AuthPacket(AuthOpcode.ClientLogonChallenge.getRawValue(), 119);
        response.body.putByte((byte)0); // unk
        response.body.putByte((byte)0); // WoW_SUCCES
        response.body.putBytes(B.asByteArray(32));
        response.body.putByte((byte)1);
        response.body.putBytes(g.asByteArray(1));
        response.body.putByte((byte)32);
        response.body.putBytes(N.asByteArray(32));
        response.body.putBytes(s.asByteArray(32));
        response.body.putBytes(new byte[16]);
        response.body.putByte((byte)0); // unk
        session.send(response);
    }

    private void clientLogonProofHandler(LogonSession session, Packet packet) {
        byte[] _A = new byte[32];
        packet.body.getBytes(_A);

        byte[] _M1 = new byte[20];
        packet.body.getBytes(_M1);

        byte[] crc_hash = new byte[20];
        packet.body.getBytes(crc_hash);

        packet.body.getByte(); // number_of_keys
        packet.body.getByte(); // unk

        // Generate u - the so called "Random scrambling parameter"
        BigNumber A = new BigNumber();
        A.setBinary(_A);

        digest.update(A.asByteArray(32));
        digest.update(B.asByteArray(32));

        BigNumber u = new BigNumber();
        u.setBinary(digest.digest());

        // Generate S - the Session key
        BigNumber S = (A.multiply(v.modPow(u, N))).modPow(b, N);

        // Generate vK - the hashed session key, hashed with H hash function
        byte[] t = S.asByteArray(32);
        byte[] t1 = new byte[16];
        byte[] vK = new byte[40];

        for (int i = 0; i < 16; i++) {
            t1[i] = t[i * 2];
        }

        digest.update(t1);

        byte[] digestBytes = digest.digest();
        for (int i = 0; i < 20; i++) {
            vK[i * 2] = digestBytes[i];
        }

        for (int i = 0; i < 16; i++) {
            t1[i] = t[i * 2 + 1];
        }

        digest.update(t1);
        digestBytes = digest.digest();
        for (int i = 0; i < 20; i++) {
            vK[i * 2 + 1] = digestBytes[i];
        }

        // generating M - the server's SRP6 M value

        digest.update(N.asByteArray(32));
        byte[] hash = digest.digest();

        digest.update(g.asByteArray(1));
        digestBytes = digest.digest();
        for (int i = 0; i < 20; i++) {
            hash[i] ^= digestBytes[i];
        }

        digest.update(session.client.username.toUpperCase().getBytes());
        byte[] t4 = digest.digest();

        BigNumber K = new BigNumber();
        K.setBinary(vK);
        BigNumber t3 = new BigNumber();
        t3.setBinary(hash);
        BigNumber t4_correct = new BigNumber();
        t4_correct.setBinary(t4);

        digest.update(t3.asByteArray());
        digest.update(t4_correct.asByteArray());
        digest.update(s.asByteArray());
        digest.update(A.asByteArray());
        digest.update(B.asByteArray());
        digest.update(K.asByteArray());

        byte[] m = digest.digest();
        BigNumber M = new BigNumber(m);
        BigNumber M1 = new BigNumber(_M1);

        if (!M.equals(M1)) {
            // TODO: Close socket
            return;
        }

        account.setSessionKey(K.asByteArray());

        digest.update(A.asByteArray());
        digest.update(_M1);
        digest.update(K.asByteArray());

        short size = 32;

        AuthPacket response = new AuthPacket(AuthOpcode.ClientLogonProof.getRawValue(), size);
        response.body.putByte((byte)0); // error
        response.body.putBytes(digest.digest());
        response.body.putInt(0x00800000); // Account flags
        response.body.putInt(0); // survey ID
        response.body.putShort((short)0); // unk2-3
        session.send(response);
    }

    private void clientRealmlistHandler(LogonSession session, Packet packet) {
        Realmlist realmlist = Realmlist.getInstance();

        short size = (short)(8 + realmlist.size());

        AuthPacket response = new AuthPacket(AuthOpcode.ClientRealmlist.getRawValue(), size + 3);
        response.body.putShort(size); // Size Placeholder
        response.body.putInt(0); // unknown?
        response.body.putShort((short)realmlist.count()); // Realm count

        // all realms
        for (Realm realm : realmlist) {
            response.body.putByte((byte)0x2A);
            response.body.putByte((byte)0);
            response.body.putByte((byte)realm.flags);
            response.body.putString(realm.name, true); // Name
            response.body.putString(realm.address, true); // Address
            response.body.putFloat(realm.population); // Population
            response.body.putByte((byte)0); // char count

            response.body.putByte((byte)1); // unk
            response.body.putByte((byte)0x2C); // unk
        }

        response.body.putShort((short)0x10);
        session.send(response.wrap());
    }
}
