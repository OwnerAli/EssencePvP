package com.mineplex.studio.essencepvp.items.actions.context;

import lombok.Builder;
import org.bukkit.event.Event;

@Builder
public record ActionContext(Event event) {}
