package ps.galaxy.streetfighter;

public class Fighter {
	private String name;
	private float height;
	private float weight;
	private String eyeColor;
	private String birthPlace ;
	private String fightingStyle;
	private int imgURL ; 
	private Power [] powers ; 

	public Fighter(String name , float height , float weight ,String eyeColor ,String birthPlace , String fightingStyle, int imgURL){
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.eyeColor = eyeColor ;
		this.birthPlace = birthPlace ;
		this.fightingStyle = fightingStyle;
		this.imgURL = imgURL;
	}
	
	public Fighter(String name , Power[] powers, int imgURL){
		this.name = name;
		this.powers = powers.clone();
		this.imgURL = imgURL;
	}
	
	public Power[] getPowers() {
		return powers.clone();
	}
	public void setPowers(Power[] powers) {
		this.powers = powers.clone();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public String getEyeColor() {
		return eyeColor;
	}
	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public String getFightingStyle() {
		return fightingStyle;
	}
	public void setFightingStyle(String fightingStyle) {
		this.fightingStyle = fightingStyle;
	}
	public int getImgURL() {
		return imgURL;
	}
	public void setImgURL(int imgURL) {
		this.imgURL = imgURL;
	}

}
