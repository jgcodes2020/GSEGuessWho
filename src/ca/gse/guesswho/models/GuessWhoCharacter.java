package ca.gse.guesswho.models;

import java.net.URL;

/**
 * Represents a Guess Who character. Contains their name, attributes, as well as a URL pointing to their picture.
 */
public class GuessWhoCharacter {

	private String name;
	private URL imageUrl;
	private byte eyeColour;
	private byte gender;
	private byte skinTone;
	private byte hairColour;
	private byte facialHair;
	private byte glasses;
	private byte visibleTeeth;
	private byte headwear;
	private byte hairStyle;
	private byte piercings;

	/**
	 * Creates a new character with the specified name, image, and attributes.
	 * @param name
	 * @param imageUrl
	 * @param eyeColour
	 * @param gender
	 * @param skinTone
	 * @param hairColour
	 * @param facialHair
	 * @param glasses
	 * @param visibleTeeth
	 * @param headwear
	 * @param hairStyle
	 * @param piercings
	 */
	public GuessWhoCharacter(String name, URL imageUrl, byte eyeColour, byte gender, byte skinTone, byte hairColour,
			byte facialHair, byte glasses, byte visibleTeeth, byte headwear, byte hairStyle, byte piercings) {
		this.name = name;
		this.imageUrl = imageUrl;
		this.eyeColour = eyeColour;
		this.gender = gender;
		this.skinTone = skinTone;
		this.hairColour = hairColour;
		this.facialHair = facialHair;
		this.glasses = glasses;
		this.visibleTeeth = visibleTeeth;
		this.headwear = headwear;
		this.hairStyle = hairStyle;
		this.piercings = piercings;
	}
	
	/**
	 * Represents brown eye colour.
	 */
	public static final byte EYE_COLOUR_BROWN = 0;
	/**
	 * Represents green eye colour.
	 */
	public static final byte EYE_COLOUR_GREEN = 1;
	/**
	 * Represents blue eye colour.
	 */
	public static final byte EYE_COLOUR_BLUE = 2;

	/**
	 * Represents male gender.
	 */
	public static final byte GENDER_MALE = 0;
	/**
	 * Represents female gender.
	 */
	public static final byte GENDER_FEMALE = 1;

	/**
	 * Represents light skin tone.
	 */
	public static final byte SKIN_TONE_LIGHT = 0;
	/**
	 * Represents dark skin tone.
	 */
	public static final byte SKIN_TONE_DARK = 1;

	/**
	 * Represents black hair colour.
	 */
	public static final byte HAIR_COLOUR_BLACK = 0;
	/**
	 * Represents brown hair colour.
	 */
	public static final byte HAIR_COLOUR_BROWN = 1;
	/**
	 * Represents ginger hair colour.
	 */
	public static final byte HAIR_COLOUR_GINGER = 2;
	/**
	 * Represents blonde hair colour.
	 */
	public static final byte HAIR_COLOUR_BLONDE = 3;
	/**
	 * Represents white hair colour.
	 */
	public static final byte HAIR_COLOUR_WHITE = 4;

	/**
	 * Represents the absence of facial hair.
	 */
	public static final byte FACIAL_HAIR_NO = 0;
	/**
	 * Represents the presence of facial hair.
	 */
	public static final byte FACIAL_HAIR_YES = 1;

	/**
	 * Represents the absence of glasses.
	 */
	public static final byte GLASSES_NO = 0;
	/**
	 * Represents the presence of glasses.
	 */
	public static final byte GLASSES_YES = 1;
	
	/**
	 * Represents the absence of visible teeth.
	 */
	public static final byte VISIBLE_TEETH_NO = 0;
	/**
	 * Represents the presence of visible teeth.
	 */
	public static final byte VISIBLE_TEETH_YES = 0;

	/**
	 * Represents the absence of headwear.
	 */
	public static final byte HEADWEAR_NO = 0;
	/**
	 * Represents the presence of headwear.
	 */
	public static final byte HEADWEAR_YES = 1;

	/**
	 * Represents a short hair style.
	 */
	public static final byte HAIR_STYLE_SHORT = 0;
	/**
	 * Represents a tied-up hair style.
	 */
	public static final byte HAIR_STYLE_TIED = 1;
	/**
	 * Represents a long hair style.
	 */
	public static final byte HAIR_STYLE_LONG = 2;
	/**
	 * Represents a bald hair style.
	 */
	public static final byte HAIR_STYLE_BALD = 3;

	/**
	 * Represents the absence of piercings.
	 */
	public static final byte PIERCINGS_NONE = 0;
	/**
	 * Represents the presence of ear piercings.
	 */
	public static final byte PIERCINGS_EAR = 1;

	/**
	 * Gets the character's name.
	 * @return the character's name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets a URL pointing to the character's image. It should usually be a local URL,
	 * such as one pointing to a local file or resource.
	 * @return a URL pointing to the character's image.
	 */
	public URL getImageUrl() {
		return this.imageUrl;
	}

	/**
	 * Gets the character's eye colour. The value should be one of the
	 * {@code EYE_COLOUR_*} constants in this class.
	 * @return the character's eye colour
	 * @see GuessWhoCharacter#EYE_COLOUR_BROWN
	 * @see GuessWhoCharacter#EYE_COLOUR_GREEN
	 * @see GuessWhoCharacter#EYE_COLOUR_BLUE
	 */
	public byte getEyeColour() {
		return this.eyeColour;
	}

	/**
	 * Gets the character's gender. The value should be one of the
	 * {@code GENDER_*} constants in this class.
	 * @return the character's gender.
	 * @see GuessWhoCharacter#GENDER_MALE
	 * @see GuessWhoCharacter#GENDER_FEMALE
	 */
	public byte getGender() {
		return this.gender;
	}

	/**
	 * Gets the character's skin tone. The value should be one of the
	 * {@code SKIN_TONE_*} constants in this class.
	 * @return the character's skin tone.
	 * @see GuessWhoCharacter#SKIN_TONE_LIGHT
	 * @see GuessWhoCharacter#SKIN_TONE_DARK
	 */
	public byte getSkinTone() {
		return this.skinTone;
	}

	/**
	 * Gets the character's hair colour. The value should be one of the
	 * {@code HAIR_COLOUR_*} constants in this class.
	 * @return the character's hair colour.
	 * @see GuessWhoCharacter#HAIR_COLOUR_BLACK
	 * @see GuessWhoCharacter#HAIR_COLOUR_BROWN
	 * @see GuessWhoCharacter#HAIR_COLOUR_GINGER
	 * @see GuessWhoCharacter#HAIR_COLOUR_BLONDE
	 * @see GuessWhoCharacter#HAIR_COLOUR_WHITE
	 */
	public byte getHairColour() {
		return this.hairColour;
	}

	/**
	 * Gets the character's facial hair style. The value should be one of the
	 * {@code FACIAL_HAIR_*} constants in this class.
	 * @return the character's facial hair style.
	 * @see GuessWhoCharacter#FACIAL_HAIR_NO
	 * @see GuessWhoCharacter#FACIAL_HAIR_YES
	 */
	public byte getFacialHair() {
		return this.facialHair;
	}

	/**
	 * Gets the character's glasses. The value should be one of the
	 * {@code GLASSES_*} constants in this class.
	 * @return the character's glasses.
	 * @see GuessWhoCharacter#GLASSES_NO
	 * @see GuessWhoCharacter#GLASSES_YES
	 */
	public byte getGlasses() {
		return this.glasses;
	}

	/**
	 * Gets the character's teeth visibility. The value should be one of the
	 * {@code VISIBLE_TEETH_*} constants in this class.
	 * @return the character's teeth visibility.
	 * @see GuessWhoCharacter#VISIBLE_TEETH_NO
	 * @see GuessWhoCharacter#VISIBLE_TEETH_YES
	 */
	public byte getVisibleTeeth() {
		return this.visibleTeeth;
	}

	/**
	 * Gets the character's headwear. The value should be one of the
	 * {@code HEADWEAR_*} constants in this class.
	 * @return the character's headwear.
	 * @see GuessWhoCharacter#HEADWEAR_NO
	 * @see GuessWhoCharacter#HEADWEAR_YES
	 */
	public byte getHeadwear() {
		return this.headwear;
	}

	/**
	 * Gets the character's hair style. The value should be one of the
	 * {@code HAIR_STYLE_*} constants in this class.
	 * @return the character's hair style.
	 * @see GuessWhoCharacter#HAIR_STYLE_SHORT
	 * @see GuessWhoCharacter#HAIR_STYLE_TIED
	 * @see GuessWhoCharacter#HAIR_STYLE_LONG
	 * @see GuessWhoCharacter#HAIR_STYLE_BALD
	 */
	public byte getHairStyle() {
		return this.hairStyle;
	}

	/**
	 * Gets the character's piercings. The value should be one of the
	 * {@code PIERCINGS_*} constants in this class.
	 * @return the character's piercings.
	 * @see GuessWhoCharacter#PIERCINGS_NONE
	 * @see GuessWhoCharacter#PIERCINGS_EAR
	 */
	public byte getPiercings() {
		return this.piercings;
	}

}
