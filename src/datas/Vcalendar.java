package datas;

public class Vcalendar {
	
	//VCALENDAR DE TYPE VEVENT SEULEMENT ?
												//La version du calendar
	private String								_version;
												//La date de début du Vevent
	private String								_dateBegin;
												//La datte de fin du Vevent
	private String								_dateEnd;
												//Le résumé du Vevent
	private String								_summary;
												//Les catégories du Vevent
	private String								_categories;
												//si la ressource affectée à l'événement est rendu indisponible
	private String								_transparency;
												//L'endroit ou a lieu le Vevent
	private String								_location;
												//La description du Vevent
	private String								_description;
	
	public Vcalendar() {
	}
	
	/*GETTERS*/
	/**
	 * @return the _version
	 */
	public String get_version() {
		return _version;
	}
	/**
	 * @return the _dateBegin
	 */
	public String get_dateBegin() {
		return _dateBegin;
	}
	/**
	 * @return the _dateEnd
	 */
	public String get_dateEnd() {
		return _dateEnd;
	}
	/**
	 * @return the _summary
	 */
	public String get_summary() {
		return _summary;
	}
	/**
	 * @return the _categories
	 */
	public String get_categories() {
		return _categories;
	}
	/**
	 * @return the _Transparency
	 */
	public String get_transparency() {
		return _transparency;
	}
	/**
	 * @return the _location
	 */
	public String get_location() {
		return _location;
	}
	/**
	 * @return the _description
	 */
	public String get_description() {
		return _description;
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
	 * @param _dateBegin the _dateBegin to set
	 */
	public void set_dateBegin(String _dateBegin) {
		this._dateBegin = _dateBegin;
	}
	/**
	 * @param _dateEnd the _dateEnd to set
	 */
	public void set_dateEnd(String _dateEnd) {
		this._dateEnd = _dateEnd;
	}
	/**
	 * @param _summary the _summary to set
	 */
	public void set_summary(String _summary) {
		this._summary = _summary;
	}
	/**
	 * @param _categories the _categories to set
	 */
	public void set_categories(String _categories) {
		this._categories = _categories;
	}
	/**
	 * @param _Transparency the _Transparency to set
	 */
	public void set_transparency(String _Transparency) {
		this._transparency = _Transparency;
	}
	/**
	 * @param _location the _location to set
	 */
	public void set_location(String _location) {
		this._location = _location;
	}
	/**
	 * @param _description the _description to set
	 */
	public void set_description(String _description) {
		this._description = _description;
	}
	/*FIN SETTERS*/
	
	/*TOSTRING*/
	public String toString() {
		String 									string = "";
		if(_version != null) 					string+= "Version:	"+get_version()+"\n";
		if(_dateBegin != null)					string+= "Date de Debut	"+get_dateBegin()+"\n";
		if(_dateEnd != null)					string+= "Date de Fin	"+get_dateEnd()+"\n";
		if(_summary != null)					string+= "Résumé		"+get_summary()+"\n";
		if(_categories != null)					string+= "Categories	"+get_categories()+"\n";
		if(_transparency != null)				string+= "Transparence	"+get_transparency()+"\n";
		if(_location != null)					string+= "Localitsation	"+get_location()+"\n";
		if(_description != null)				string+= "Description	"+get_description()+"\n";
		return string;
		
	}

	
	/*FIN TOSTRING*/
}
