package net.lldv.mobearn.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDeathEvent;
import net.lldv.mobearn.MobEarn;
import net.lldv.mobearn.utils.MobEarnMob;
import net.lldv.llamaeconomy.LlamaEconomy;

public class EntityListener implements Listener {

    private final MobEarn plugin;

    public EntityListener(MobEarn plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(EntityDeathEvent event) {
        if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent cause = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
            if (cause.getDamager() instanceof Player) {
                Player player = (Player) cause.getDamager();

                int networkId = event.getEntity().getNetworkId();

                if(this.plugin.getMobs().containsKey(networkId)) {
                    MobEarnMob mob = this.plugin.getMobs().get(networkId);
                    payout(player, mob.getName(), mob.getMoney());
                }
            }
        }
    }

    public void payout(Player player, String mob, double money) {
        LlamaEconomy.getAPI().addMoney(player, money);

        if(!this.plugin.getMessageType().equals("none")) {
            String notification = this.plugin.getEarnMessage().replace("{MONEY}", String.valueOf(money))
                    .replace("{MOB}", mob);
            if(this.plugin.getMessageType().equals("popup")) {
                player.sendPopup(notification);
            } else if (this.plugin.getMessageType().equals("chat")) {
                player.sendMessage(notification);
            } else {
                plugin.getLogger().error("Invalid Message Type: " + this.plugin.getMessageType());
                player.sendMessage("null");
            }
        }
    }

}



