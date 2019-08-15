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

package net.llamagames.MobEarn.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDeathEvent;
import me.onebone.economyapi.EconomyAPI;
import net.llamagames.MobEarn.MobEarn;
import net.llamagames.MobEarn.utils.MobEarnMob;

public class EntityListener implements Listener {

    private MobEarn plugin;
    private EconomyAPI economyAPI;

    public EntityListener(MobEarn plugin, EconomyAPI economyAPI) {
        this.plugin = plugin;
        this.economyAPI = economyAPI;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent cause = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
            if (cause.getDamager() instanceof Player) {
                Player player = (Player) cause.getDamager();

                int networkId = event.getEntity().getNetworkId();

                if(MobEarn.mobs.containsKey(networkId)) {
                    MobEarnMob mob = MobEarn.mobs.get(networkId);
                    payout(player, mob.getName(), mob.getMoney());
                }
            }
        }
    }

    public void payout(Player player, String mob, double money) {
        economyAPI.addMoney(player, money);

        if(!MobEarn.messageType.equalsIgnoreCase("none")) {
            String notification = MobEarn.earnMessage.replace("{MONEY}", String.valueOf(money))
                    .replace("{MOB}", mob);
            if(MobEarn.messageType.equalsIgnoreCase("popup")) {
                player.sendPopup(notification);
            } else if (MobEarn.messageType.equalsIgnoreCase("chat")) {
                player.sendMessage(notification);
            } else {
                plugin.getLogger().error("INVALID MESSAGE TYPE: " + MobEarn.messageType);
                player.sendMessage("null");
            }
        }
    }

}



