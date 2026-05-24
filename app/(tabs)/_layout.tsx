import { Tabs } from "expo-router";
import React from "react";

import { HapticTab } from "@/src/components/common/haptic-tab";
import { IconSymbol } from "../../src/components/ui/icon-symbol";
import { Colors } from "../../src/constants/theme";
import { useUserContext } from "../../src/context/user.context";
import { useColorScheme } from "../../src/hooks/use-color-scheme";

export default function TabLayout() {
  const user = useUserContext();
  const colorScheme = useColorScheme();

  return (
    <Tabs
      screenOptions={{
        tabBarActiveTintColor: Colors[colorScheme ?? "light"].tint,
        headerShown: false,
        tabBarButton: HapticTab,
      }}
    >
      <Tabs.Screen
        name="home"
        options={{
          title: "Home",
          tabBarIcon: ({ color }) => (
            <IconSymbol size={28} name="house.fill" color={color} />
          ),
        }}
      />

      <Tabs.Screen
        name="list-of-world-cups"
        options={{
          title: "world cups",
          tabBarIcon: ({ color }) => (
            <IconSymbol size={28} name="soccerball" color={color} />
          ),
        }}
      />

    </Tabs>
  );
}
