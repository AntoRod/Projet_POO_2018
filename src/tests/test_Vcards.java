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
												//Défini si le maximum de fichiers à afficher est initialisé
	private static Boolean						_isMaximumSet = false;
	private static int							_maximumArg;
												//Défini si un dossier spécifique à afficher est initialisé en entrée (avec affichage des formats voulu)
	private static Boolean						_isDisplaySet = false;
	private static int							_displayArg;
												//Défini si un type de fichier à afficher a été spécifié (peut en contenir plusieurs)
	private static Boolean						_isTypeOfDisplaySet;
	private static String[]						_typeOfFiles = null;
												//Défini si un fichier est initialisé en entrée
	private static Boolean						_isInputSet = false;
	private static int							_inputArg;
												//Défini si un fichier est initialisé en sortie
	private static Boolean						_isOutputSet = false;
	private static int							_outputArg;
												//Défini si un fichier HTML est initialisé en sortie
	private static Boolean						_isHTMLSet = false;
	private static int							_HTMLArg;
												//Défini si plusieurs fichiers sont initialisés en sortie
	private static Boolean						_isMultipleOutputSet;
	private static int[]						_multipleOutputArg;
												//Défini si une erreur est entrée en paramètre
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
			//Le nombre maximum de fichiers à afficher
			
			//On analyse les arguments
			for(int i=0;i<args.length;i++) {
				//Si l'argument est -M: la taille maximale d'affichage des fichiers
				if("-M".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
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
				//Si l'argument est -d: on lance la méthode qui liste les fichiers dans le répertoire (argument suivant)
				if("-d".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
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
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
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
			//Si on a une erreur de display, on affiche le message d'erreur, le cas d'erreur est traité automatiquement dans l'affichage (affichage par défaut)
			if(_errorType.contains("DISPLAY")) {
				System.out.println("Bad display argument, displaying default location");
				Argument_Management.displayVcardsInDirectory("./", _typeOfFiles, maximum);
			}
			//Si on a une erreur d'input, on affiche le message d'erreur, on ne peut pas afficher le contenu d'input dans ce cas ci (pas de cas par défaut)
			if(_errorType.contains("INPUT")) System.out.println("Bad input argument, can't import a proper file");
		}
		
		//TRAITEMENT DES ARGUMENTS
		//Si le maximum est défini
		if(_isMaximumSet) {
			maximum = Integer.parseInt(args[_maximumArg]);
		}
		//Si le mode display est défini
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
