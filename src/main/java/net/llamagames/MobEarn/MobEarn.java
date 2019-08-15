/*
    MobEarn:

    Copyright (C) 2019 SchdowNVIDIA
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

    Thanks to ZAP-Hosting.com and JetBrains!

    ZAP-Hosting.com gave me a Server for testing all plugins.
    If you're interested in a cheap VPS or strong Rootserver follow the links below:
    VPS: https://zap-hosting.com/schdowvserver
    Rootserver: https://zap-hosting.com/schdowroot
    Code (10% Discount Lifetime): schdow-10
 */

package net.llamagames.MobEarn;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import me.onebone.economyapi.EconomyAPI;
import net.llamagames.MobEarn.listener.EntityListener;
import net.llamagames.MobEarn.utils.MobEarnMob;

import java.util.HashMap;
import java.util.Map;

public class MobEarn extends PluginBase {

    public static String messageType;
    public static String earnMessage;

    public static HashMap<Integer, MobEarnMob> mobs = new HashMap<>();

    @Override
    public void onEnable() {
        init();
    }

    public void init() {
        this.saveDefaultConfig();
        loadConfig();
        getServer().getPluginManager().registerEvents(new EntityListener(this, EconomyAPI.getInstance()), this);
    }

    @SuppressWarnings("unchecked")
    public void loadConfig() {
        Config config = getConfig();

        for (Map.Entry<String, Object> map: config.getSection("mobs").getAllMap().entrySet()) {
            String name = (String) map.getKey();
            HashMap<String, Object> mob = (HashMap<String, Object>) map.getValue();

            double money = (double) mob.get("money");
            int networkid = (int) mob.get("networkid");

            mobs.put(networkid, new MobEarnMob(name, money));
        }

        messageType = config.getString("message-type");
        earnMessage = config.getString("message");
    }

}



