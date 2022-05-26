package fun.mcbee.bot.commands;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class tickets implements EventListener {

	@Override
	public void onEvent(GenericEvent event) {
		if(event instanceof MessageReceivedEvent) {
			Command((MessageReceivedEvent) event);
		}
	}

	private void Command(MessageReceivedEvent event) {
		if(event.getMessage().getContentRaw().toLowerCase().equals("-new")) {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();
			
			if(event.getGuild().getTextChannelsByName(event.getAuthor().getId()+"s-ticket", true).size() == 0) {
				event.getGuild().createTextChannel(event.getAuthor().getId()+"s-ticket", event.getGuild().getCategoryById("979501165974794312")).queue((ticketChannel) -> {
					
					ticketChannel.createPermissionOverride(event.getMember()).setAllow(Permission.VIEW_CHANNEL).queue();
					ticketChannel.createPermissionOverride(event.getGuild().getRoleById("922587254378094644")).setAllow(Permission.VIEW_CHANNEL).queue();
					
					EmbedBuilder created = new EmbedBuilder();
					created.setColor(new Color(138,43,226));
					created.setTitle("Ticket | "+event.getGuild().getName());
					created.setDescription("<@"+event.getAuthor().getId()+"> you ticket is created.");
					created.setFooter(event.getGuild().getName()+"\n"+dtf.format(now), event.getGuild().getIconUrl());
					created.setThumbnail(event.getGuild().getIconUrl());
					event.getMessage().replyEmbeds(created.build()).queue();
					
					EmbedBuilder embed = new EmbedBuilder();
					embed.setColor(new Color(255, 191, 0));
					embed.setTitle("Ticket | "+event.getGuild().getName());
					embed.setDescription("**"+event.getAuthor().getName()+"** this is your ticket. Let us know what you need.");
					embed.setFooter(event.getGuild().getName()+"\n"+dtf.format(now), event.getGuild().getIconUrl());
					embed.setThumbnail(event.getGuild().getIconUrl());
					ticketChannel.sendMessageEmbeds(embed.build()).queue();
					ticketChannel.sendMessage(event.getAuthor().getAsMention()).queue();
				});		
			} else {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(new Color(242, 140, 40));
				embed.setTitle("Ticket | "+event.getGuild().getName());
				embed.setDescription("<@"+event.getAuthor().getId()+"> you already have an open ticket.");
				embed.setFooter(event.getGuild().getName()+"\n"+dtf.format(now), event.getGuild().getIconUrl());
				embed.setThumbnail(event.getGuild().getIconUrl());
				event.getMessage().replyEmbeds(embed.build()).queue();
				for(TextChannel channel : event.getGuild().getTextChannelsByName(event.getAuthor().getId()+"s-ticket", true)) {
					channel.sendMessage("<@"+event.getAuthor().getId()+">").queue();;
				}
			}
		} else if(event.getMessage().getContentRaw().toLowerCase().equals("-close")) {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();
			
			if(event.getTextChannel().getParentCategory().getId().equals("979501165974794312")) {
				if(event.getMember().getRoles().contains(event.getGuild().getRoleById("922587254378094644"))) {
					EmbedBuilder embed = new EmbedBuilder();
					embed.setColor(new Color(242, 140, 40));
					embed.setTitle("Ticket | "+event.getGuild().getName());
					embed.setDescription("This ticket will be deleted in 10 seconds");
					embed.setFooter(event.getGuild().getName()+"\n"+dtf.format(now), event.getGuild().getIconUrl());
					embed.setThumbnail(event.getGuild().getIconUrl());
					event.getMessage().replyEmbeds(embed.build()).queue();
					event.getTextChannel().delete().queueAfter(10, TimeUnit.SECONDS);
				} else {
					event.getMessage().reply("🤚 You do not have permissions.").queue();
				}
			}
		}
	}
}
