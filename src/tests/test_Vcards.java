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
	private static int							_maximum = -1;
												//Défini si un dossier spécifique à afficher est initialisé en entrée (avec affichage des formats voulu)
	private static Boolean						_isDisplaySet = false;
	private static int							_displayArg;
												//Défini si un type de fichier à afficher a été spécifié (peut en contenir plusieurs)
	private static Boolean						_isTypeOfDisplaySet;
	private static ArrayList<String>			_typeOfFiles = null;
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
	
												//La class qui gère la Vcard
	private static Vcard_Management 			_vcard_management;

	public static void main(String[] args) {
		
		
		/*
		 *	PARTIE GESTION DES ARGUMENTS
		 *		- ANALYSE DES ARGUMENTS
		 *		- TRAITEMENT DES ERREURS "NOARGUMENT"
		 *		- TRAITEMENT DES ARGUMENTS (si les arguments ne sont pas invalides, on passe au traitement et l'analyse en profondeur des arguments)
		 *			- TRAITEMENT DES ERREURS "BADARGUMENT"
		 */			
		
		//ANALYSE DES ARGUMENTS
		//Si aucun argument, on affiche le mode d'emploi du logiciel
		if(args.length == 0) Argument_Management.displayManual();
		//Sinon on analyse les arguments
		if(args.length != 0) {

			//ORDRE: M d t i o h
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
						_errorType += "NOMAXIMUM;";
					}
				}
				
				//Si l'argument est -d: on défini le mode display et le fichier à display (en argument)
				if("-d".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isDisplaySet = true;
						_displayArg = i+1;
					}
					//Sinon avertir d'une erreur
					else {
						_isErrorSet = true;
						_errorType += "NODISPLAY;";
					}
				}
				
				//Si l'argument est -t: on défini les types de fichiers à afficher dans le mode display
				if("-t".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isTypeOfDisplaySet = true;
						//On défini un compteur de fichier
						int counter = 0;
						_typeOfFiles = new ArrayList<String>();
						//Tant que l'argument est valide (par rapport à l'argument parametré)
						while(Argument_Management.isValidArgument(args, i+1+counter)) {
							//On ajoute le type de fichier à la liste
//							System.out.println(args[i+1+counter]);
							_typeOfFiles.add(args[i+1+counter]);
							//On incrémente le compteur
							counter++;
						}
					}
					//Sinon avertir d'une erreur
					else {
						_isErrorSet = true;
						_errorType += "NOTYPE;";
					}
				}
				
				//Si l'argument est -d: on défini le mode input et le fichier à input (en argument)
				if("-i".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isInputSet = true;
						_inputArg = i+1;
					}
					//Sinon avertir d'une erreur
					else {
						_isErrorSet = true;
						_errorType +="NOINPUT;";
					}
				}
				//Si l'argument est -o: on défini le mode output et le fichier à output (en argument)
				if("-o".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isOutputSet = true;
						_outputArg = i+1;
					}
					//Sinon avertir d'une erreur
					else {
						_isErrorSet = true;
						_errorType += "NOOUTPUT;";
					}
				}
				
				//Si l'argument est -h: on défini le mode HTML et le fichier à output (en argument)
				if("-h".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isHTMLSet = true;
						_HTMLArg = i+1;
					}
					//Sinon avertir d'une erreur
					else {
						_isErrorSet = true;
						_errorType += "NOHTML;";
					}
				}
				
			}
		}
		
		
		//TRAITEMENT DES ERREURS DE TYPE "NOARGUMENT"
		//GESTION PAR EXCEPTION PLUS TARD, DEJA FINIR TOUT LES PARAMETRES
		if(_errorType != null) {
			//Si on a une erreur de display, on affiche le message d'erreur, le cas d'erreur est traité automatiquement dans l'affichage (affichage par défaut)
			if(_errorType.contains("NODISPLAY")) {
				System.out.println("No display argument, displaying default location.");
				Argument_Management.displayVcardsInDirectory("./", _typeOfFiles, _maximum);
			}
			//Si on a une erreur d'input, on affiche le message d'erreur, on ne peut pas afficher le contenu d'input dans ce cas ci (pas de cas par défaut)
			if(_errorType.contains("NOINPUT")) System.out.println("No input argument, can't import a proper file.");
			//Si on a une erreur d'output, on affiche le message d'erreur, définir un fichier d'output par défaut ? (je pense)
			if(_errorType.contains("NOOUTPUT")) System.out.println("No outpout argument, can't export the file properly.");
			if(_errorType.contains("NOHTML")) System.out.println("No HTML argument, can't export the file properly.");
		}
		
		//TRAITEMENT DES ARGUMENTS
		//Si le maximum est défini, on initialise le maximum (utilisé dans les méthodes de display)
		if(_isMaximumSet) {
			_maximum = Integer.parseInt(args[_maximumArg]);
		}
		
		//Si le mode display est défini, on affiche les éléments du type voulu dans le dossier passé en argument
		if(_isDisplaySet) {
			Argument_Management.displayVcardsInDirectory(args[_displayArg], _typeOfFiles, _maximum);
		}
		
		//Si le mode input est défini, on crée la Vcard/Le calendrier(WIP) à partir du fichier d'input
		if(_isInputSet) {
			//On analyse la Vcard pour en extraire les informations
			_vcard_management = new Vcard_Management();
			_vcard_management.analyzeFile(args[_inputArg]);
			//Si on ne prévoit pas d'output le fichier (serialisé ou HTML), on affiche le contenu de la Vcard (traitée)
			if(!_isOutputSet && !_isHTMLSet) System.out.println(_vcard_management.get_vcard());
		}
		
		//Si le mode output est défini, on crée alors le fichier attendu (.ser ou autre)
		if(_isOutputSet) {
			//Si on a un fichier en input, on peut alors le sérialiser
			if(_isInputSet) {
				//On serialise le fichier
				_vcard_management.serializeVcard(args[_outputArg]);
				System.out.println("The Vcard has been successfully exported as "+args[_outputArg]);
			}
			//Sinon avertir qu'on ne peut pas exporter un fichier qui n'a pas été importé au préalable
			else System.out.println("No file has been imported, cannot export nothing.");
		}
		
		//Si le mode HTML est défini, on crée alors le fichier attendu (.html)
		if(_isHTMLSet) {
			//Si on a un fichier en input, on peut alors le convertir en fichier HTML
			if(_isInputSet) {
				//METTRE LE PARAMETRE APRES
				_vcard_management.convertVcardToHTMLCode();
				System.out.println("The Vcard has been successfully exported as "+args[_HTMLArg]);
			}
			//Sinon avertir qu'on ne peut pas exporter un fichier qui n'a pas été importé au préalable
			else System.out.println("No file has been imported, cannot export nothing.");
		}
		
		
		/*FIN PARTIE GESTION DES ARGUMENTS*/

	}
	
}
