/*
GuessWhoCharacter.java
Authors: Jacky Guo, Chapman Yu
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.models;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * Represents a Guess Who character. Is intended to be an immutable data class.
 */
public class GuessWhoCharacter {

	private String name;
	private Image image;
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
	 * 
	 * @param name         the character's name
	 * @param eyeColour    the character's eye colour, should be one of the
	 *                     {@code GuessWhoCharacter.EYE_COLOUR_*} constants
	 * @param gender       the character's gender, should be one of the
	 *                     {@code GuessWhoCharacter.GENDER_*} constants
	 * @param skinTone     the character's skin tone, should be one of the
	 *                     {@code GuessWhoCharacter.SKIN_TONE_*} constants
	 * @param hairColour   the character's hair colour, should be one of the
	 *                     {@code GuessWhoCharacter.HAIR_COLOUR_*} constants
	 * @param facialHair   the character's facial hair, should be one of the
	 *                     {@code GuessWhoCharacter.FACIAL_HAIR_*} constants
	 * @param glasses      the character's glasses, should be one of the
	 *                     {@code GuessWhoCharacter.GLASSES_*} constants
	 * @param visibleTeeth the character's teeth visibility, should be one of the
	 *                     {@code GuessWhoCharacter.VISIBLE_TEETH_*} constants
	 * @param headwear     the character's headwear, should be one of the
	 *                     {@code GuessWhoCharacter.HEADWEAR_*} constants
	 * @param hairStyle    the character's gender, should be one of the
	 *                     {@code GuessWhoCharacter.HAIR_STYLE_*} constants
	 * @param piercings    the character's gender, should be one of the
	 *                     {@code GuessWhoCharacter.PIERCINGS_*} constants
	 */
	public GuessWhoCharacter(String name, Image image, byte eyeColour, byte gender, byte skinTone, byte hairColour,
			byte facialHair, byte glasses, byte visibleTeeth, byte headwear, byte hairStyle, byte piercings) {
		this.name = name;
		this.image = image;
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
	 * Parses a Guess Who character from a row of CSV. TODO: document format
	 * 
	 * @param row the CSV row to read
	 * @return a Guess Who character based on the provided data.
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static GuessWhoCharacter fromCsvRow(String row) throws NumberFormatException, IOException {
		String[] parts = row.split(",");
		if (parts.length != 12)
			throw new IllegalArgumentException("Invalid CSV row: not exactly 12 fields");
		
		// convert the stored path to a URL referencing the relevant file or JAR path
		String path = "/ca/gse/guesswho/" + parts[1];
		URL imageURL = GuessWhoCharacter.class.getResource(path);
		// load that as an image
		Image image = ImageIO.read(imageURL);
		
		return new GuessWhoCharacter(
				parts[0],
				image,
				// all of these correspond to integer constants in this class
				Byte.parseByte(parts[2]),
				Byte.parseByte(parts[3]),
				Byte.parseByte(parts[4]),
				Byte.parseByte(parts[5]),
				Byte.parseByte(parts[6]),
				Byte.parseByte(parts[7]),
				Byte.parseByte(parts[8]),
				Byte.parseByte(parts[9]),
				Byte.parseByte(parts[10]),
				Byte.parseByte(parts[11]));
	}

	/**
	 * Represents the eye colour attribute.
	 */
	public static final int ATTRIBUTE_EYE_COLOUR = 0;
	/**
	 * Represents the gender attribute.
	 */
	public static final int ATTRIBUTE_GENDER = 1;
	/**
	 * Represents the skin tone attribute.
	 */
	public static final int ATTRIBUTE_SKIN_TONE = 2;
	/**
	 * Represents the hair colour attribute.
	 */
	public static final int ATTRIBUTE_HAIR_COLOUR = 3;
	/**
	 * Represents the facial hair attribute.
	 */
	public static final int ATTRIBUTE_FACIAL_HAIR = 4;
	/**
	 * Represents the glasses attribute.
	 */
	public static final int ATTRIBUTE_GLASSES = 5;
	/**
	 * Represents the visible teeth attribute.
	 */
	public static final int ATTRIBUTE_VISIBLE_TEETH = 6;
	/**
	 * Represents the headwear attribute.
	 */
	public static final int ATTRIBUTE_HEADWEAR = 7;
	/**
	 * Represents the hair style attribute.
	 */
	public static final int ATTRIBUTE_HAIR_STYLE = 8;
	/**
	 * Represents the piercings attribute.
	 */
	public static final int ATTRIBUTE_PIERCINGS = 9;
	/**
	 * Upper bound on the indices allocated for attribute codes.
	 */
	public static final int ATTRIBUTE_NUM_VALS = 10;

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
	 * Upper bound on the indices allocated for eye colour values.
	 */
	public static final byte EYE_COLOUR_NUM_VALS = 3;

	/**
	 * Represents male gender.
	 */
	public static final byte GENDER_MALE = 0;
	/**
	 * Represents female gender.
	 */
	public static final byte GENDER_FEMALE = 1;
	/**
	 * Upper bound on the indices allocated for gender values.
	 */
	public static final byte GENDER_NUM_VALS = 2;

	/**
	 * Represents light skin tone.
	 */
	public static final byte SKIN_TONE_LIGHT = 0;
	/**
	 * Represents dark skin tone.
	 */
	public static final byte SKIN_TONE_DARK = 1;
	/**
	 * Upper bound on the indices allocated for gender values.
	 */
	public static final byte SKIN_TONE_NUM_VALS = 2;

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
	 * Upper bound on the indices allocated for hair colour values.
	 */
	public static final byte HAIR_COLOUR_NUM_VALS = 5;

	/**
	 * Represents the absence of facial hair.
	 */
	public static final byte FACIAL_HAIR_NO = 0;
	/**
	 * Represents the presence of facial hair.
	 */
	public static final byte FACIAL_HAIR_YES = 1;
	public static final byte FACIAL_HAIR_NUM_VALS = 2;

	/**
	 * Represents the absence of glasses.
	 */
	public static final byte GLASSES_NO = 0;
	/**
	 * Represents the presence of glasses.
	 */
	public static final byte GLASSES_YES = 1;
	/**
	 * Upper bound on the indices allocated for glasses values.
	 */
	public static final byte GLASSES_NUM_VALS = 2;
	
	/**
	 * Represents the absence of visible teeth.
	 */
	public static final byte VISIBLE_TEETH_NO = 0;
	/**
	 * Represents the presence of visible teeth.
	 */
	public static final byte VISIBLE_TEETH_YES = 1;
	/**
	 * Upper bound on the indices allocated for visible teeth values.
	 */
	public static final byte VISIBLE_TEETH_NUM_VALS = 2;

	/**
	 * Represents the absence of headwear.
	 */
	public static final byte HEADWEAR_NO = 0;
	/**
	 * Represents the presence of headwear.
	 */
	public static final byte HEADWEAR_YES = 1;
	/**
	 * Upper bound on the indices allocated for headwear values.
	 */
	public static final byte HEADWEAR_NUM_VALS = 2;

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
	 * Upper bound on the indices allocated for hair style values.
	 */
	public static final byte HAIR_STYLE_NUM_VALS = 4;

	/**
	 * Represents the absence of piercings.
	 */
	public static final byte PIERCINGS_NO = 0;
	/**
	 * Represents the presence of ear piercings.
	 */
	public static final byte PIERCINGS_YES = 1;
	/**
	 * Upper bound on the indices allocated for piercings values.
	 */
	public static final byte PIERCINGS_NUM_VALS = 2;

	/**
	 * Gets the character's name.
	 * 
	 * @return the character's name.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the URL of this character's image.
	 * @return the URL of this character's image.
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * Selects and returns an attribute according to an attribute code.
	 * This avoids the need for reflectively calling one of the {@code get*()}
	 * methods.
	 * 
	 * @param attributeCode one of the {@code ATTRIBUTE_*} values on this class.
	 * @return the attribute corresponding to {@code attributeCode}
	 */
	public byte getAttribute(int attributeCode) {
		// switch on the attribute, return the corresponding field
		// I could make this an array of byte, but this makes for less readable code
		switch (attributeCode) {
			case ATTRIBUTE_EYE_COLOUR:
				return this.eyeColour;
			case ATTRIBUTE_GENDER:
				return this.gender;
			case ATTRIBUTE_SKIN_TONE:
				return this.skinTone;
			case ATTRIBUTE_HAIR_COLOUR:
				return this.hairColour;
			case ATTRIBUTE_FACIAL_HAIR:
				return this.facialHair;
			case ATTRIBUTE_GLASSES:
				return this.glasses;
			case ATTRIBUTE_VISIBLE_TEETH:
				return this.visibleTeeth;
			case ATTRIBUTE_HEADWEAR:
				return this.headwear;
			case ATTRIBUTE_HAIR_STYLE:
				return this.hairStyle;
			case ATTRIBUTE_PIERCINGS:
				return this.piercings;
			default:
				throw new IllegalArgumentException("Invalid attribute code " + attributeCode);
		}
	}

	/**
	 * Determines the maximum value allocated for a specific attribute code.
	 * 
	 * @param attributeCode one of the {@code ATTRIBUTE_*} values on this class.
	 * @return
	 */
	public static byte attributeMaxValue(int attributeCode) {
		// switch on the attribute code, return the corresponding constant
		switch (attributeCode) {
			case ATTRIBUTE_EYE_COLOUR:
				return EYE_COLOUR_NUM_VALS;
			case ATTRIBUTE_GENDER:
				return GENDER_NUM_VALS;
			case ATTRIBUTE_SKIN_TONE:
				return SKIN_TONE_NUM_VALS;
			case ATTRIBUTE_HAIR_COLOUR:
				return HAIR_COLOUR_NUM_VALS;
			case ATTRIBUTE_FACIAL_HAIR:
				return FACIAL_HAIR_NUM_VALS;
			case ATTRIBUTE_GLASSES:
				return GLASSES_NUM_VALS;
			case ATTRIBUTE_VISIBLE_TEETH:
				return VISIBLE_TEETH_NUM_VALS;
			case ATTRIBUTE_HEADWEAR:
				return HEADWEAR_NUM_VALS;
			case ATTRIBUTE_HAIR_STYLE:
				return HAIR_STYLE_NUM_VALS;
			case ATTRIBUTE_PIERCINGS:
				return PIERCINGS_NUM_VALS;
			default:
				throw new IllegalArgumentException("Invalid attribute code " + attributeCode);
		}
	}

	/**
	 * Gets the character's eye colour. The value should be one of the
	 * {@code EYE_COLOUR_*} constants in this class.
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
	 * @return the character's piercings.
	 * @see GuessWhoCharacter#PIERCINGS_NO
	 * @see GuessWhoCharacter#PIERCINGS_YES
	 */
	public byte getPiercings() {
		return this.piercings;
	}
	
	@Override
	public int hashCode() {
		// We use Java's default hash combiner to merge all these values into
		// a single hash code. I could implement this myself, but it wouldn't be
		// worth it.
		return Objects.hash(
			name, eyeColour, gender, skinTone, hairColour, facialHair, 
			glasses, visibleTeeth, headwear, hairStyle, piercings);
	}

	/**
	 * Returns a descriptive string listing all the character's attributes.
	 * 
	 * @return a descriptive string listing all the character's attributes.
	 */
	@Override
	public String toString() {
		// Create a StringBuilder to build the name piecewise
		StringBuilder output = new StringBuilder();
		output.append(this.name).append(" (");

		// switch over values for each attribute
		// this literally repeats for all of them, so I won't add more comments
		switch (this.eyeColour) {
			case EYE_COLOUR_BROWN:
				output.append("brown eyes");
				break;
			case EYE_COLOUR_GREEN:
				output.append("green eyes");
				break;
			case EYE_COLOUR_BLUE:
				output.append("blue eyes");
				break;
			default:
				// this should never happen, the assertion should never trigger
				// same goes for all the others
				assert false;
		}
		output.append(", ");

		switch (this.gender) {
			case GENDER_MALE:
				output.append("male");
				break;
			case GENDER_FEMALE:
				output.append("female");
				break;
			default:
				assert false;
		}
		output.append(", ");

		switch (this.skinTone) {
			case SKIN_TONE_LIGHT:
				output.append("light skin tone");
				break;
			case SKIN_TONE_DARK:
				output.append("dark skin tone");
				break;
			default:
				assert false;
		}
		output.append(", ");

		switch (this.hairColour) {
			case HAIR_COLOUR_BLACK:
				output.append("black hair");
				break;
			case HAIR_COLOUR_BROWN:
				output.append("brown hair");
				break;
			case HAIR_COLOUR_GINGER:
				output.append("ginger hair");
				break;
			case HAIR_COLOUR_BLONDE:
				output.append("blonde hair");
				break;
			case HAIR_COLOUR_WHITE:
				output.append("white hair");
				break;
			default:
				assert false;
		}
		output.append(", ");

		switch (this.facialHair) {
			case FACIAL_HAIR_NO:
				output.append("no facial hair");
				break;
			case FACIAL_HAIR_YES:
				output.append("facial hair");
				break;
			default:
				assert false;
		}
		output.append(", ");

		switch (this.glasses) {
			case GLASSES_NO:
				output.append("no glasses");
				break;
			case GLASSES_YES:
				output.append("glasses");
				break;
			default:
				assert false;
		}
		output.append(", ");

		switch (this.visibleTeeth) {
			case VISIBLE_TEETH_NO:
				output.append("no visible teeth");
				break;
			case VISIBLE_TEETH_YES:
				output.append("visible teeth");
				break;
			default:
				assert false;
		}
		output.append(", ");

		switch (this.headwear) {
			case HEADWEAR_NO:
				output.append("no headwear");
				break;
			case HEADWEAR_YES:
				output.append("headwear");
				break;
			default:
				assert false;
		}
		output.append(", ");

		switch (this.hairStyle) {
			case HAIR_STYLE_SHORT:
				output.append("short hair");
				break;
			case HAIR_STYLE_TIED:
				output.append("tied-up hair");
				break;
			case HAIR_STYLE_LONG:
				output.append("long hair");
				break;
			case HAIR_STYLE_BALD:
				output.append("bald");
				break;
			default:
				assert false;
		}
		output.append(", ");

		switch (this.piercings) {
			case PIERCINGS_NO:
				output.append("no piercings");
				break;
			case PIERCINGS_YES:
				output.append("piercings");
				break;
			default:
				assert false;
		}
		// add closing bracket, output value (it's gonna be pretty long)
		output.append(")");
		return output.toString();
	}
}
