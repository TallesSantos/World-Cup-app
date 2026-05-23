import { Tabs } from "expo-router";
import React from "react";

import { HapticTab } from "../../src/components/haptic-tab";
import { IconSymbol } from "../../src/components/ui/icon-symbol";
import { Colors } from "../../src/constants/theme";
import { useColorScheme } from "../../src/hooks/use-color-scheme";
import { useUserContext } from "../../src/shared/context/user.context";

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
        name="private-user-page"
        options={{
          title: "Personal",
          tabBarIcon: ({ color }) => (
            <IconSymbol size={28} name="person.fill" color={color} />
          ),
        }}
      />

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
        name="world_cup_page"
        options={{
          title: "world cup",
          tabBarIcon: ({ color }) => (
            <IconSymbol size={28} name="cart" color={color} />
          ),
        }}
      />

    </Tabs>
  );
}
