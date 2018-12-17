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
	private static int							_maximum = -1;
												//D�fini si un dossier sp�cifique � afficher est initialis� en entr�e (avec affichage des formats voulu)
	private static Boolean						_isDisplaySet = false;
	private static int							_displayArg;
												//D�fini si un type de fichier � afficher a �t� sp�cifi� (peut en contenir plusieurs)
	private static Boolean						_isTypeOfDisplaySet;
	private static ArrayList<String>			_typeOfFiles = null;
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
	
												//La class qui g�re la Vcard
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
					//Si l'argument suivant est d�fini correctement (n'est pas vide ou n'est pas un "-param�tre")
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
				
				//Si l'argument est -d: on d�fini le mode display et le fichier � display (en argument)
				if("-d".equals(args[i])) {
					//Si l'argument suivant est d�fini correctement (n'est pas vide ou n'est pas un "-param�tre")
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
				
				//Si l'argument est -t: on d�fini les types de fichiers � afficher dans le mode display
				if("-t".equals(args[i])) {
					//Si l'argument suivant est d�fini correctement (n'est pas vide ou n'est pas un "-param�tre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isTypeOfDisplaySet = true;
						//On d�fini un compteur de fichier
						int counter = 0;
						_typeOfFiles = new ArrayList<String>();
						//Tant que l'argument est valide (par rapport � l'argument parametr�)
						while(Argument_Management.isValidArgument(args, i+1+counter)) {
							//On ajoute le type de fichier � la liste
//							System.out.println(args[i+1+counter]);
							_typeOfFiles.add(args[i+1+counter]);
							//On incr�mente le compteur
							counter++;
						}
					}
					//Sinon avertir d'une erreur
					else {
						_isErrorSet = true;
						_errorType += "NOTYPE;";
					}
				}
				
				//Si l'argument est -d: on d�fini le mode input et le fichier � input (en argument)
				if("-i".equals(args[i])) {
					//Si l'argument suivant est d�fini correctement (n'est pas vide ou n'est pas un "-param�tre")
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
				//Si l'argument est -o: on d�fini le mode output et le fichier � output (en argument)
				if("-o".equals(args[i])) {
					//Si l'argument suivant est d�fini correctement (n'est pas vide ou n'est pas un "-param�tre")
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
				
				//Si l'argument est -h: on d�fini le mode HTML et le fichier � output (en argument)
				if("-h".equals(args[i])) {
					//Si l'argument suivant est d�fini correctement (n'est pas vide ou n'est pas un "-param�tre")
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
			//Si on a une erreur de display, on affiche le message d'erreur, le cas d'erreur est trait� automatiquement dans l'affichage (affichage par d�faut)
			if(_errorType.contains("NODISPLAY")) {
				System.out.println("No display argument, displaying default location.");
				Argument_Management.displayVcardsInDirectory("./", _typeOfFiles, _maximum);
			}
			//Si on a une erreur d'input, on affiche le message d'erreur, on ne peut pas afficher le contenu d'input dans ce cas ci (pas de cas par d�faut)
			if(_errorType.contains("NOINPUT")) System.out.println("No input argument, can't import a proper file.");
			//Si on a une erreur d'output, on affiche le message d'erreur, d�finir un fichier d'output par d�faut ? (je pense)
			if(_errorType.contains("NOOUTPUT")) System.out.println("No outpout argument, can't export the file properly.");
			if(_errorType.contains("NOHTML")) System.out.println("No HTML argument, can't export the file properly.");
		}
		
		//TRAITEMENT DES ARGUMENTS
		//Si le maximum est d�fini, on initialise le maximum (utilis� dans les m�thodes de display)
		if(_isMaximumSet) {
			_maximum = Integer.parseInt(args[_maximumArg]);
		}
		
		//Si le mode display est d�fini, on affiche les �l�ments du type voulu dans le dossier pass� en argument
		if(_isDisplaySet) {
			Argument_Management.displayVcardsInDirectory(args[_displayArg], _typeOfFiles, _maximum);
		}
		
		//Si le mode input est d�fini, on cr�e la Vcard/Le calendrier(WIP) � partir du fichier d'input
		if(_isInputSet) {
			//On analyse la Vcard pour en extraire les informations
			_vcard_management = new Vcard_Management();
			_vcard_management.analyzeFile(args[_inputArg]);
			//Si on ne pr�voit pas d'output le fichier (serialis� ou HTML), on affiche le contenu de la Vcard (trait�e)
			if(!_isOutputSet && !_isHTMLSet) System.out.println(_vcard_management.get_vcard());
		}
		
		//Si le mode output est d�fini, on cr�e alors le fichier attendu (.ser ou autre)
		if(_isOutputSet) {
			//Si on a un fichier en input, on peut alors le s�rialiser
			if(_isInputSet) {
				//On serialise le fichier
				_vcard_management.serializeVcard(args[_outputArg]);
				System.out.println("The Vcard has been successfully exported as "+args[_outputArg]);
			}
			//Sinon avertir qu'on ne peut pas exporter un fichier qui n'a pas �t� import� au pr�alable
			else System.out.println("No file has been imported, cannot export nothing.");
		}
		
		//Si le mode HTML est d�fini, on cr�e alors le fichier attendu (.html)
		if(_isHTMLSet) {
			//Si on a un fichier en input, on peut alors le convertir en fichier HTML
			if(_isInputSet) {
				//METTRE LE PARAMETRE APRES
				_vcard_management.convertVcardToHTMLCode();
				System.out.println("The Vcard has been successfully exported as "+args[_HTMLArg]);
			}
			//Sinon avertir qu'on ne peut pas exporter un fichier qui n'a pas �t� import� au pr�alable
			else System.out.println("No file has been imported, cannot export nothing.");
		}
		
		
		/*FIN PARTIE GESTION DES ARGUMENTS*/

	}
	
}
