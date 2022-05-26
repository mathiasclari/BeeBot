package fun.mcbee.bot;

import javax.security.auth.login.LoginException;

import fun.mcbee.bot.commands.tickets;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class BeeBot {

public static JDA jda;
	
	public static void main(String[] args) throws LoginException, InterruptedException {
		jda = JDABuilder.create("OTY1NzQyOTM2MDk4NzM0MDgx.G2wOFb.AWGirMuC5zUlnksVVBcDTkp4hk4BKtLjLDLqDg", GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES).build();
		jda.awaitReady();
		jda.getPresence().setActivity(Activity.playing("play.mcbee.fun"));
		jda.addEventListener(new tickets());
	}

}
