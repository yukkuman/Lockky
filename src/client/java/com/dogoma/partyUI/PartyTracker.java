package com.dogoma.partyUI;

import java.util.HashSet;
import java.util.Set;

public class PartyTracker {
    public static String currentOwner = null;
    public static final Set<String> currentMembers = new HashSet<>();

    public static void resetParty() {
        currentOwner = null;
        currentMembers.clear();
        System.out.println("❌ パーティー状態リセット");
    }

    public static void join(String playerName) {
        currentMembers.add(playerName);
        System.out.println("✅ 参加: " + playerName);
    }

    public static void leave(String playerName) {
        currentMembers.remove(playerName);
        System.out.println("🚪 離脱: " + playerName);
    }

    public static void setLeader(String playerName) {
        currentOwner = playerName;
        currentMembers.add(playerName); // 念のため追加
        System.out.println("👑 新リーダー: " + playerName);
    }
}