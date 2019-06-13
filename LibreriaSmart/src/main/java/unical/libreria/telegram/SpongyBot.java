package unical.libreria.telegram;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import unical.libreria.detection.OcrDetection;

public class SpongyBot extends TelegramLongPollingBot{
	
	Boolean find=false;
	
	
	public void onUpdateReceived(Update update) {

		//Stringa test per vedere su console i messaggi
		System.out.println(update.getMessage().getText());
		
		
		//        System.out.println(update.getMessage().getFrom().getFirstName() );

		String command=update.getMessage().getText();

		SendMessage message = new SendMessage();
		message.setChatId(update.getMessage().getChatId());
	if(find==false)	{	
			//Comando Ping
			if(command.equals("/ping")){
				message.setText("Sono pronto");
				sendMessaggio(message);
			}
			//Comando Cerca/Find
			else if(command.equals("/find")){
				message.setText("Ok inserisci il titolo del libro da cercare");
				sendMessaggio(message);
				
				find=true;
			
			}
			//Comando Reboot
			else if(command.equals("/reboot")){
				reboot(message);
			}
			//Comando Non riconosciuto
			else {
				message.setText("Comando non Riconosciuto");
				sendMessaggio(message);
			}

	}
	else if(!command.equals("/find")) {
		find(message, update);
		find=false;
	}
		
		//Comando test 


		//        if(command.equals("/myname")){
		//
		//            System.out.println(update.getMessage().getFrom().getFirstName());
		//
		//            message.setText(update.getMessage().getFrom().getFirstName());
		//        }
		//
		//        if (command.equals("/mylastname")){
		//
		//            System.out.println(update.getMessage().getFrom().getLastName());
		//            message.setText(update.getMessage().getFrom().getLastName());
		//        }
		//
		//        if (command.equals("/myfullname")){
		//            System.out.println(update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName());
		//       message.setText(update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getLastName());
		//        }
		//
		//        message.setChatId(update.getMessage().getChatId());


		//		try {
		//			execute(message);
		//		} catch (TelegramApiException e) {
		//			e.printStackTrace();
		//		}


	}
	


	public void find(SendMessage message, Update update) {
		
		//Comando terminale cercalibro
		OcrDetection od = new OcrDetection();
		
		try {
			if(od.FindText(update.getMessage().getText())) {
				message.setText("Libro Trovato!Sarà acceso un led.");
				sendMessaggio(message);
			}
			else {
				message.setText("Libro NON trovato...");
				sendMessaggio(message);
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

	public void reboot(SendMessage message) {
		message.setText("Il Sistema sarà riavviato ora, ci potranno volere diversi minuti");
		sendMessaggio(message);
		try {
			Process p = Runtime.getRuntime().exec("reboot");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessaggio(SendMessage message) {
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public String getBotUsername() {
		return "spongy_bot";
	}

	public String getBotToken() {
		return "779119938:AAFaQE-q1grAVDl4yLVhONGaTGnpL6hbjjk";
	}
	

	
	

}
