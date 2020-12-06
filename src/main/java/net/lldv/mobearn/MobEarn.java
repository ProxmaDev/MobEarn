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

package net.lldv.mobearn;

import cn.nukkit.entity.mob.EntityGhast;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import lombok.Getter;
import net.lldv.mobearn.listener.EntityListener;
import net.lldv.mobearn.utils.MobEarnMob;

import java.util.HashMap;
import java.util.Map;

public class MobEarn extends PluginBase {

    @Getter
    private String messageType;
    @Getter
    private String earnMessage;

    @Getter
    private final HashMap<Integer, MobEarnMob> mobs = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new EntityListener(this), this);

        final Config config = getConfig();

        for (final Map.Entry<String, Object> map: config.getSection("mobs").getAllMap().entrySet()) {
            String name = map.getKey();
            HashMap<String, Object> mob = (HashMap<String, Object>) map.getValue();

            final double money = (double) mob.get("money");
            final int networkid = (int) mob.get("networkid");

            this.mobs.put(networkid, new MobEarnMob(name, money));
        }

        this.messageType = config.getString("message-type").toLowerCase();
        this.earnMessage = config.getString("message");
    }


}



