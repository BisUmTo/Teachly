function customBlocksToolbox() {
    return {
        "kind": "category",
        "name": "Triggers",
        "category_style": "variable_dynamic_blocks",
        "expanded": "true",
        "contents" : [
            {
                "kind": "category",
                "name": "Blocks",
                "category_style": "variable_dynamic_blocks",
                "contents": [
                    {
                        "kind": "block",
                        "type": "assign_random_exercise"
                    }
                ]
            },
            {
                "kind": "category",
                "name": "Examples",
                "category_style": "variable_dynamic_blocks",
                "contents" : [
                    {
                        "kind": "label",
                        "text": "When a player jumps ..."
                    },
                    {
                        "kind": "block",
                        "type": "event_procedure",
                        "fields": {
                            "EVENT": "com.destroystokyo.paper.event.player.PlayerJumpEvent"
                        },
                        "inputs": {
                            "STATEMENT": {
                                "block": {
                                    "type": "assign_random_exercise",
                                    "inputs": {
                                        "PLAYER": {
                                            "block": {
                                                "type": "event_getter",
                                                "fields": {
                                                    "EVENT": "com.destroystokyo.paper.event.player.PlayerJumpEvent",
                                                    "GETTER": "getPlayer;;org.bukkit.entity.Player,org.bukkit.entity.HumanEntity,org.bukkit.conversations.Conversable,org.bukkit.OfflinePlayer,org.bukkit.plugin.messaging.PluginMessageRecipient,net.kyori.adventure.identity.Identified,net.kyori.adventure.bossbar.BossBarViewer,com.destroystokyo.paper.network.NetworkClient,org.bukkit.entity.LivingEntity,org.bukkit.entity.AnimalTamer,org.bukkit.inventory.InventoryHolder,org.bukkit.attribute.Attributable,org.bukkit.entity.Damageable,org.bukkit.projectiles.ProjectileSource,io.papermc.paper.entity.Frictional,org.bukkit.entity.Entity,org.bukkit.metadata.Metadatable,org.bukkit.command.CommandSender,org.bukkit.Nameable,org.bukkit.persistence.PersistentDataHolder,net.kyori.adventure.text.event.HoverEventSource<net.kyori.adventure.text.event.HoverEvent.ShowEntity>,net.kyori.adventure.sound.Sound.Emitter,net.kyori.adventure.audience.Audience,org.bukkit.permissions.Permissible,io.papermc.paper.persistence.PersistentDataViewHolder,org.bukkit.permissions.ServerOperator,org.bukkit.configuration.serialization.ConfigurationSerializable"
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    {
                        "kind": "label",
                        "text": "When a player picks up the entire stack of items ..."
                    },
                    {
                        "kind": "block",
                        "type": "event_procedure",
                        "fields": {
                            "EVENT": "org.bukkit.event.player.PlayerPickupItemEvent"
                        },
                        "inputs": {
                            "STATEMENT": {
                                "block": {
                                    "type": "controls_if",
                                    "inputs": {
                                        "IF0": {
                                            "block": {
                                                "type": "logic_compare",
                                                "fields": {
                                                    "OP": "EQ"
                                                },
                                                "inputs": {
                                                    "A": {
                                                        "block": {
                                                            "type": "event_getter",
                                                            "fields": {
                                                                "EVENT": "org.bukkit.event.player.PlayerPickupItemEvent",
                                                                "GETTER": "getRemaining;;Number"
                                                            }
                                                        }
                                                    },
                                                    "B": {
                                                        "block": {
                                                            "type": "math_number",
                                                            "fields": {
                                                                "NUM": 0
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        },
                                        "DO0": {
                                            "block": {
                                                "type": "assign_random_exercise",
                                                "inputs": {
                                                    "PLAYER": {
                                                        "block": {
                                                            "type": "event_getter",
                                                            "fields": {
                                                                "EVENT": "org.bukkit.event.player.PlayerPickupItemEvent",
                                                                "GETTER": "getPlayer;;org.bukkit.entity.Player,org.bukkit.entity.HumanEntity,org.bukkit.conversations.Conversable,org.bukkit.OfflinePlayer,org.bukkit.plugin.messaging.PluginMessageRecipient,net.kyori.adventure.identity.Identified,net.kyori.adventure.bossbar.BossBarViewer,com.destroystokyo.paper.network.NetworkClient,org.bukkit.entity.LivingEntity,org.bukkit.entity.AnimalTamer,org.bukkit.inventory.InventoryHolder,org.bukkit.attribute.Attributable,org.bukkit.entity.Damageable,org.bukkit.projectiles.ProjectileSource,io.papermc.paper.entity.Frictional,org.bukkit.entity.Entity,org.bukkit.metadata.Metadatable,org.bukkit.command.CommandSender,org.bukkit.Nameable,org.bukkit.persistence.PersistentDataHolder,net.kyori.adventure.text.event.HoverEventSource<net.kyori.adventure.text.event.HoverEvent.ShowEntity>,net.kyori.adventure.sound.Sound.Emitter,net.kyori.adventure.audience.Audience,org.bukkit.permissions.Permissible,io.papermc.paper.persistence.PersistentDataViewHolder,org.bukkit.permissions.ServerOperator,org.bukkit.configuration.serialization.ConfigurationSerializable"
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                ]
            }
        ]
    }
}