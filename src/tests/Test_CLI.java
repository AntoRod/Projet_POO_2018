package tests;


import java.util.ArrayList;

import exceptions.BadArgumentException;
import exceptions.NoArgumentException;
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
 * 
 */


public class Test_CLI {
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
	private static Boolean						_isHTMLCodeSet = false;
	private static int							_HTMLCodeArg;
												//Défini si un fichier HTML est initialisé en sortie
	private static Boolean						_isHTMLPageSet = false;
	private static int							_HTMLPageArg;
												//Défini si une erreur est entrée en paramètre
	private static String						_errorType = null;
	
												//La class qui gère la Vcard
	private static Vcard_Management 			_vcard_management;
												//La class qui gère le Vcalendar
	private static Vcalendar_Management 		_vcalendar_management;

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
					else _errorType += "NOMAXIMUM;";
				}
				
				//Si l'argument est -d: on défini le mode display et le fichier à display (en argument)
				if("-d".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isDisplaySet = true;
						_displayArg = i+1;
					}
					//Sinon avertir d'une erreur
					else _errorType += "NODISPLAY;";
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
					else _errorType += "NOTYPE;";
				}
				
				//Si l'argument est -d: on défini le mode input et le fichier à input (en argument)
				if("-i".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isInputSet = true;
						_inputArg = i+1;
					}
					//Sinon avertir d'une erreur
					else _errorType +="NOINPUT;";
				}
				//Si l'argument est -o: on défini le mode output et le fichier à output (en argument)
				if("-o".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isOutputSet = true;
						_outputArg = i+1;
					}
					//Sinon avertir d'une erreur
					else _errorType += "NOOUTPUT;";
				}
				
				//Si l'argument est -h: on défini le mode HTML et le fichier à output (en argument)
				if("-h".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isHTMLCodeSet = true;
						_HTMLCodeArg = i+1;
					}
					//Sinon avertir d'une erreur
					else _errorType += "NOHTML;";
				}
				if("-p".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
					if(Argument_Management.isValidArgument(args, i+1)) {
						_isHTMLPageSet = true;
						_HTMLPageArg = i+1;
					}
					//Sinon avertir d'une erreur
					else _errorType += "NOHTML;";
				}
				
			}
		}
		
		

		try {
			//TRAITEMENT DES ERREURS DE TYPE "NOARGUMENT"
			//GESTION PAR EXCEPTION PLUS TARD, DEJA FINIR TOUT LES PARAMETRES
			if(_errorType != null) {
				//Si on a une erreur de display, on affiche le message d'erreur, le cas d'erreur est traité automatiquement dans l'affichage (affichage par défaut)
				//AUCUNE EXCEPTION DANS CE CAS LA
				if(_errorType.contains("NODISPLAY")) {
					System.out.println("No display argument, displaying default location.");
					Argument_Management.displayVcardsInDirectory("./", _typeOfFiles, _maximum);
				}
				//Si on a une erreur d'input, on affiche le message d'erreur, on ne peut pas afficher le contenu d'input dans ce cas ci (pas de cas par défaut)
				if(_errorType.contains("NOINPUT")) throw new NoArgumentException("No input argument, can't import a proper file.");
				//Si on a une erreur d'output, on affiche le message d'erreur, définir un fichier d'output par défaut ? (je pense)
				if(_errorType.contains("NOOUTPUT")) throw new NoArgumentException("No outpout argument, can't export the file properly.");
				if(_errorType.contains("NOHTML")) throw new NoArgumentException("No HTML argument, can't export the file properly.");
			}
		
			//TRAITEMENT DES ARGUMENTS
			//Si le maximum est défini, on initialise le maximum (utilisé dans les méthodes de display)
			if(_isMaximumSet) {
				_maximum = Integer.parseInt(args[_maximumArg]);
			}
			
			//Si le mode display est défini, on affiche les éléments du type voulu dans le dossier passé en argument
			if(_isDisplaySet) {
				//Si le type de display n'est pas défini, on en défini un par défaut, sinon on laisse les types comme ils sont
				//Si le type choisi est invalide (mauvais argument) rien ne sera affiché, pas besoin de traiter ce cas d'erreur
				if(!_isTypeOfDisplaySet) {
					//Si la liste des formats est vide, mettre par défaut
					_typeOfFiles = new ArrayList<String>();
					//Types par défaut: Vcard et Calendar
					_typeOfFiles.add(".vcf");
					_typeOfFiles.add(".ics");
				}
				//Dans tout les cas, on affiche en fonction de _typeOfFiles passé en argument
				Argument_Management.displayVcardsInDirectory(args[_displayArg], _typeOfFiles, _maximum);
			}
			
			//Si le mode input est défini, on crée la Vcard/Le calendrier(WIP) à partir du fichier d'input
			if(_isInputSet) {
				if(args[_inputArg].endsWith(".vcf")) {
					//On analyse la Vcard pour en extraire les informations
					_vcard_management = new Vcard_Management(args[_inputArg]);
					//Si on ne prévoit pas d'output le fichier (serialisé ou HTML), on affiche le contenu de la Vcard (traitée)
					if(!_isOutputSet && !_isHTMLCodeSet && !_isHTMLPageSet) System.out.println(_vcard_management.get_vcard());
				}
				//Sinon si c'est un iCalendar
				else if(args[_inputArg].endsWith(".ics")) {
					//On analyse le Vcalendar pour en extraire les informations
					_vcalendar_management = new Vcalendar_Management(args[_inputArg]);
					//Si on ne prévoit pas d'output le fichier (serialisé ou HTML), on affiche le contenu de la Vcard (traitée)
					if(!_isOutputSet && !_isHTMLCodeSet && !_isHTMLPageSet) System.out.println(_vcalendar_management.get_vcalendar());
				}

			}
			
			//Si le mode output est défini, on crée alors le fichier attendu
			if(_isOutputSet) {
				//Si on a un fichier en input, on peut alors le sérialiser
				if(_isInputSet) {
					//Si c'est une Vcard
					if(args[_inputArg].endsWith(".vcf")) {
						//On serialise le fichier
						_vcard_management.serializeVcard(args[_outputArg]);
						//On affiche qu'on a réussi le traitement dans l'invite de commande
						System.out.println("The Vcard has been successfully exported as "+args[_outputArg]);
					}
					//Sinon si c'est un iCalendar
					else if(args[_inputArg].endsWith(".ics")) {
						//TRAITEMENT ICS
						
					}
					//Sinon exception BadArgument
					else throw new BadArgumentException("No file has been imported, cannot export nothing.");

				}
				//Sinon avertir qu'on ne peut pas exporter un fichier qui n'a pas été importé au préalable
				else throw new BadArgumentException("No file has been imported, cannot export nothing.");
			}
			
			//Si le mode HTMLCode est défini, on converti la Vcard en un fragment HTML puis on crée le fichier comprenant ce code.
			//NOTE: le fichier sera invalide car le code généré n'est que la partie Vcard, pour crée la page complète on utilise l'argument "-p"
			if(_isHTMLCodeSet) {
				//Si on a un fichier en input, on peut alors le convertir en fichier HTML
				if(_isInputSet) {
					//Si le fichier est bien au format HTML, on crée le fichier
					if(args[_HTMLCodeArg].endsWith(".html")) {
						//Si on a input une Vcard
						if(args[_inputArg].endsWith(".vcf")) {
							String HTMLCode = _vcard_management.convertVcardToHTMLCode();
							Directory_Management.convertStringToHTMLFile(args[_HTMLCodeArg], HTMLCode);
							System.out.println("The Vcard has been successfully exported as "+args[_HTMLCodeArg]);
						}
						//Si on a input un Vcalendar
						else if(args[_inputArg].endsWith(".ics")) {
							String HTMLCode = _vcalendar_management.convertVcalendarToHTMLCode();
							Directory_Management.convertStringToHTMLFile(args[_HTMLCodeArg], HTMLCode);
							System.out.println("The Vcalendar has been successfully exported as "+args[_HTMLCodeArg]);
						}
						else throw new BadArgumentException("The input file is neither a Vcard or a Vcalendar, can't convert it into HTML Code.");

					}
					//Sinon avertir qu'on ne peut pas exporter un fichier HTML d'autre chose qu'ne Vcard ou un Vcalendar
					else throw new BadArgumentException("Can't export HTML Fragment in a not HTML File.");
				}
				//Sinon avertir qu'on ne peut pas exporter un fichier qui n'a pas été importé au préalable, EXCEPTION ?
				else throw new NoArgumentException("No file has been imported, cannot export nothing.");
			}
			
			//Si le mode HTMLPage est défini, on converti la Vcard en une page HTML 5 complète et valide
			if(_isHTMLPageSet) {
				//Si on a un fichier en input, on peut alors le convertir en fichier HTML
				if(_isInputSet) {
					//Si le fichier est bien au format HTML, on crée le fichier
					String HTMLPage = null;
					if(args[_HTMLPageArg].endsWith(".html")) {
						//Si on a input une Vcard
						if(args[_inputArg].endsWith(".vcf")) {
							HTMLPage = _vcard_management.convertVcardToHTMLPage();
							System.out.println("The Vcard has been successfully exported as "+args[_HTMLPageArg]);
						}
						//Si on a input un Vcalendar
						else if(args[_inputArg].endsWith("ics")) {
							HTMLPage = _vcalendar_management.convertVcalendarToHTMLPage();
							System.out.println("The Vcaendar has been successfully exported as "+args[_HTMLPageArg]);
						}
						//Sinon avertir qu'on ne peut pas exporter un fichier HTML d'autre chose qu'ne Vcard ou un Vcalendar
						else throw new BadArgumentException("The input file is neither a Vcard or a Vcalendar, can't convert it into HTML Code.");
						//Si on a pu récupérer le code, on le converti
						if(HTMLPage != null) Directory_Management.convertStringToHTMLFile(args[_HTMLPageArg], HTMLPage);
					}
					//Sinon avertir qu'on ne peut pas exporter un fichier HTML au format autre que HTML, EXCEPTION ? (changer le type d'argument)
					else throw new BadArgumentException("Can't export HTML Fragment in a not HTML File.");
				}
				//Sinon avertir qu'on ne peut pas exporter un fichier qui n'a pas été importé au préalable, EXCEPTION ?
				else throw new NoArgumentException("No file has been imported, cannot export nothing.");
			}
			
			
		//On attrape les exceptions personnalisées
		//NOTE: on affiche le message d'erreur correspondant, on ne traite aucune autre information car ce sont des erreurs d'argument (mauvais input/output)
		} catch (BadArgumentException f) {System.out.println(f.getMessage());}
		  catch (NoArgumentException g) {System.out.println(g.getMessage());}
		
		/*FIN PARTIE GESTION DES ARGUMENTS*/

	}
	
}
