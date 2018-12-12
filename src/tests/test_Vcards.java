package tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import datas.Vcard;
//import datas.*;
import file_management.*;

/*
 * MODE CONSOLE: 
 * 			- AUCUN ARGUMENT: FONCTIONNEL
 * 			- GESTION DES ARGUMENTS: EN COURS
 * 			- TRAITEMENT DU FICHIER VCARD EN ENTREE: EN COURS
 * 			- TRAITEMENT DU FICHIER SERIALISE DE LA VCARD EN SORTIE: EN COURS
 * 			- AFFICHAGE DES VCARD: EN COURS
 * 			- AFFICHAGE DE LA LISTE DES VCARDS: FONCTIONNEL
 * 
 */


public class test_Vcards {
												//D�fini si le maximum de fichiers � afficher est initialis�
	private static Boolean						_isMaximumSet = false;
	private static int							_maximumArg;
												//D�fini si un dossier sp�cifique � afficher est initialis� en entr�e (avec affichage des formats voulu)
	private static Boolean						_isDisplaySet = false;
	private static int							_displayArg;
												//D�fini si un type de fichier � afficher a �t� sp�cifi� (peut en contenir plusieurs)
	private static Boolean						_isTypeOfDisplaySet;
	private static String[]						_typeOfFiles = null;
												//D�fini si un fichier est initialis� en entr�e
	private static Boolean						_isInputSet = false;
	private static int							_inputArg;
												//D�fini si un fichier est initialis� en sortie
	private static Boolean						_isOutputSet = false;
	private static int							_outputArg;
												//D�fini si un fichier HTML est initialis� en sortie
	private static Boolean						_isHTMLSet = false;
	private static int							_HTMLArg;
												//D�fini si plusieurs fichiers sont initialis�s en sortie
	private static Boolean						_isMultipleOutputSet;
	private static int[]						_multipleOutputArg;
												//D�fini si une erreur est entr�e en param�tre
	private static Boolean						_isErrorSet;
	private static String						_errorType = null;

	public static void main(String[] args) {
		
		
		/*
		 *	PARTIE GESTION DES ARGUMENTS
		 *		- ANALYSE DES ARGUMENTS
		 *		- TRAITEMENT DES ARGUMENTS
		 */
		
		//ANALYSE DES ARGUMENTS
		int maximum = -1;
		if(args.length == 0) Argument_Management.displayManual();
		
		if(args.length != 0) {
			//Le nombre maximum de fichiers � afficher
			
			//On analyse les arguments
			for(int i=0;i<args.length;i++) {
				//Si l'argument est -M: la taille maximale d'affichage des fichiers
				if("-M".equals(args[i])) {
					//Si l'argument suivant est d�fini correctement (n'est pas vide ou n'est pas un "-param�tre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isMaximumSet = true;
						_maximumArg = i+1;
					}
					//Sinon avertir d'une erreur
					else {
						_isErrorSet = true;
						_errorType += "MAXIMUM;";
					}
				}
				//Si l'argument est -d: on lance la m�thode qui liste les fichiers dans le r�pertoire (argument suivant)
				if("-d".equals(args[i])) {
					//Si l'argument suivant est d�fini correctement (n'est pas vide ou n'est pas un "-param�tre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isDisplaySet = true;
						_displayArg = i+1;
					}
					//Sinon avertir d'une erreur
					else {
						_isErrorSet = true;
						_errorType += "DISPLAY;";
					}
				}
				if("-i".equals(args[i])) {
					//Si l'argument suivant est d�fini correctement (n'est pas vide ou n'est pas un "-param�tre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isInputSet = true;
						_inputArg = i+1;
					}
					//Sinon avertir d'une erreur
					else {
						_isErrorSet = true;
						_errorType +="INPUT;";
					}
				}
			}
		}
		
		
		//TRAITEMENT DES ERREURS
		if(_errorType != null) {
			//Si on a une erreur de display, on affiche le message d'erreur, le cas d'erreur est trait� automatiquement dans l'affichage (affichage par d�faut)
			if(_errorType.contains("DISPLAY")) {
				System.out.println("Bad display argument, displaying default location");
				Argument_Management.displayVcardsInDirectory("./", _typeOfFiles, maximum);
			}
			//Si on a une erreur d'input, on affiche le message d'erreur, on ne peut pas afficher le contenu d'input dans ce cas ci (pas de cas par d�faut)
			if(_errorType.contains("INPUT")) System.out.println("Bad input argument, can't import a proper file");
		}
		
		//TRAITEMENT DES ARGUMENTS
		//Si le maximum est d�fini
		if(_isMaximumSet) {
			maximum = Integer.parseInt(args[_maximumArg]);
		}
		//Si le mode display est d�fini
		if(_isDisplaySet) {
			Argument_Management.displayVcardsInDirectory(args[_displayArg], _typeOfFiles, maximum);
		}
		if(_isInputSet) {
			Vcard test_Card = new Vcard();
			Vcard_Management vcard_management = new Vcard_Management(test_Card);
			vcard_management.analyzeFile(args[_inputArg]);
			System.out.println(test_Card);
		}
		


		
		
		/*FIN PARTIE GESTION DES ARGUMENTS*/

	}
	
}
