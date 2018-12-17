package datas;

import java.io.Serializable;

public class Vcard implements Serializable{
	
	/*	La classe contenant les informations des Vcard à traiter
	 *	Pour la date de dernière modification: on analyse son format pendant la modification de celle-ci (seulement si besoin de modifier la date)
	 *
	 *
	 *	CREATION DE LA VCARD: FONCTIONNEL
	 *	EXTRACTION DES DONNEES DU FICHIER DANS LA VCARD: FONCTIONNEL + EN COURS (le reste des cas possible d'informations)
	 *	TOSTRING MINIMAL NON AUTOMATIQUE: FONCTIONNEL
	 *	AMELIORER LE TOSTRING EN FONCTION DES ATTRIBUTS DE MANIERE AUTOMATIQUE: WIP
	 *	SEPARATION DES VCARDS: EN COURS
	 */
												//La serialVersionUID générée par défaut
	private static final long 					serialVersionUID = 2586331209324394623L;
												//La version de la Vcard
	private String								_version;
												//Le nom de la personne
	private String								_name;
												//le prénom de la personne
	private String								_firstName;
												//Son numéro de téléphone de maison
	private String								_homePhone;
												//Son adresse e-mail
	private String								_mail;
												//Son adresse perso
	private String								_homeAdress;
												//Le label de son adresse perso
	private String								_labelHomeAdress;
												//Le nom de son entreprise (s'il est dans une entreprise)
	private String								_company;
												//Son numéro de téléphone de travail
	private String								_workPhone;
												//Son adresse de travail
	private String								_workAdress;
												//Le label de son adresse de travail
	private String								_labelWorkAdress;
												//Son titre (fonction dans l'entreprise)
	private String								_title;
												//Le lien de sa photo (s'il en possède une)
	private String								_picture;
												//La date de dernière modification de la Vcard
	private String								_lastUpdated;
	
	
	public Vcard() {
		
	}

	/*GETTERS*/
	/**
	 * @return the _name
	 */
	public String get_name() {
		return _name;
	}
	/**
	 * @return the _version
	 */
	public String get_version() {
		return _version;
	}

	/**
	 * @return the _firstName
	 */
	public String get_firstName() {
		return _firstName;
	}


	/**
	 * @return the _homePhone
	 */
	public String get_homePhone() {
		return _homePhone;
	}


	/**
	 * @return the _workPhone
	 */
	public String get_workPhone() {
		return _workPhone;
	}


	/**
	 * @return the _title
	 */
	public String get_title() {
		return _title;
	}


	/**
	 * @return the _picture
	 */
	public String get_picture() {
		return _picture;
	}


	/**
	 * @return the _company
	 */
	public String get_company() {
		return _company;
	}


	/**
	 * @return the _workAdress
	 */
	public String get_workAdress() {
		return _workAdress;
	}


	/**
	 * @return the _homeAdress
	 */
	public String get_homeAdress() {
		return _homeAdress;
	}


	/**
	 * @return the _labelWorkAdress
	 */
	public String get_labelWorkAdress() {
		return _labelWorkAdress;
	}


	/**
	 * @return the _labelHomeAdress
	 */
	public String get_labelHomeAdress() {
		return _labelHomeAdress;
	}


	/**
	 * @return the _mail
	 */
	public String get_mail() {
		return _mail;
	}


	/**
	 * @return the _lastUpdated
	 */
	public String get_lastUpdated() {
		return _lastUpdated;
	}
	/*FIN GETTERS*/
	/*SETTERS*/
	/**
	 * @param _version the _version to set
	 */
	public void set_version(String _version) {
		this._version = _version;
	}
	/**
	 * @param _name the _name to set
	 */
	public void set_name(String _name) {
		this._name = _name;
	}


	/**
	 * @param _firstName the _firstName to set
	 */
	public void set_firstName(String _firstName) {
		this._firstName = _firstName;
	}


	/**
	 * @param _homePhone the _homePhone to set
	 */
	public void set_homePhone(String _homePhone) {
		this._homePhone = _homePhone;
	}


	/**
	 * @param _workPhone the _workPhone to set
	 */
	public void set_workPhone(String _workPhone) {
		this._workPhone = _workPhone;
	}


	/**
	 * @param _title the _title to set
	 */
	public void set_title(String _title) {
		this._title = _title;
	}


	/**
	 * @param _picture the _picture to set
	 */
	public void set_picture(String _picture) {
		this._picture = _picture;
	}


	/**
	 * @param _company the _company to set
	 */
	public void set_company(String _company) {
		this._company = _company;
	}


	/**
	 * @param _workAdress the _workAdress to set
	 */
	public void set_workAdress(String _workAdress) {
		this._workAdress = _workAdress;
	}


	/**
	 * @param _homeAdress the _homeAdress to set
	 */
	public void set_homeAdress(String _homeAdress) {
		this._homeAdress = _homeAdress;
	}


	/**
	 * @param _labelWorkAdress the _labelWorkAdress to set
	 */
	public void set_labelWorkAdress(String _labelWorkAdress) {
		this._labelWorkAdress = _labelWorkAdress;
	}


	/**
	 * @param _labelHomeAdress the _labelHomeAdress to set
	 */
	public void set_labelHomeAdress(String _labelHomeAdress) {
		this._labelHomeAdress = _labelHomeAdress;
	}


	/**
	 * @param _mail the _mail to set
	 */
	public void set_mail(String _mail) {
		this._mail = _mail;
	}


	/**
	 * @param _lastUpdated the _lastUpdated to set
	 */
	public void set_lastUpdated(String _lastUpdated) {
		this._lastUpdated = _lastUpdated;
	}
	/*FIN SETTERS*/

	/*TOSTRING*/
	public String toString() {
		
		String string ="";
		if(_version != null) 					string+="Version:		"+get_version()+"\n";
		if(_name != null) 						string+="Name:			"+get_name()+"\n";
		if(_firstName != null)					string+="First Name:		"+get_firstName()+"\n";
		if(_homePhone != null)					string+="Home Phone:		"+get_homePhone()+"\n";
		if(_mail != null)						string+="Mail:			"+get_mail()+"\n";
		if(_homeAdress != null)					string+="Home:			"+get_homeAdress()+"\n";
		if(_labelHomeAdress != null)			string+=" - Label		"+get_labelHomeAdress()+"\n";
		if(_company != null)					string+="Company:		"+get_company()+"\n";
		if(_workPhone != null)					string+="Work Phone:		"+get_workPhone()+"\n";
		if(_workAdress != null)					string+="Work Adress:		"+get_workAdress()+"\n";
		if(_labelWorkAdress != null)			string+=" - Label:		"+get_labelWorkAdress()+"\n";
		if(_title != null)						string+="Title:			"+get_title()+"\n";
		if(_picture != null)					string+="Picture:		"+get_picture()+"\n";
		if(_lastUpdated != null)				string+="Last Modified:	"+get_lastUpdated()+"\n";
		
		return string;		
	}
	/*FIN TOSTRING*/
	
}
