//-- generated code, will be overwritten at each recompilation

package rs2d.sequence.onpulseslc;

import rs2d.spinlab.tools.param.*;
import rs2d.spinlab.tools.table.*;
import rs2d.spinlab.tools.role.RoleEnum;
import rs2d.spinlab.sequenceGenerator.GeneratorParamEnum;

import static java.util.Arrays.asList;

public enum OnepulseSlcParams implements GeneratorParamEnum {
    ACCU_DIM {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("ACCU_DIM");
            param.setDisplayedName("ACCU_DIM.name");
            param.setDescription("ACCU_DIM.description");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Integer);
            param.setMinValue(0);
            param.setMaxValue(3);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    },

    ACQUISITION_MATRIX_DIMENSION_1D {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("ACQUISITION_MATRIX_DIMENSION_1D");
            param.setDisplayedName("Acquisition 1D");
            param.setDescription("The acquisition size of the first dimension");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(8192);
            param.setDefaultValue(128);
            return param;
        }
    },

    ACQUISITION_MATRIX_DIMENSION_2D {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("ACQUISITION_MATRIX_DIMENSION_2D");
            param.setDisplayedName("Acquisition 2D");
            param.setDescription("The acquisition size of the second dimension");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(1);
            param.setDefaultValue(128);
            return param;
        }
    },

    ACQUISITION_MATRIX_DIMENSION_3D {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("ACQUISITION_MATRIX_DIMENSION_3D");
            param.setDisplayedName("Acquisition 3D");
            param.setDescription("The acquisition size of the third dimension");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    },

    ACQUISITION_MATRIX_DIMENSION_4D {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("ACQUISITION_MATRIX_DIMENSION_4D");
            param.setDisplayedName("Acquisition 4D");
            param.setDescription("The acquisition size of the fourth dimension");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    },

    ACQUISITION_TIME_PER_SCAN {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("ACQUISITION_TIME_PER_SCAN");
            param.setDisplayedName("Acq Time");
            param.setDescription("The acquisition time per scan");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(0.40894464);
            param.setDefaultValue(1.0);
            return param;
        }
    },

    BASE_FREQ_1 {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("BASE_FREQ_1");
            param.setDisplayedName("Base Freq 1");
            param.setDescription("The base frequency of the first sequence channel");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(1.2818100000000001E8);
            param.setDefaultValue(1.27194553E8);
            return param;
        }
    },

    BASE_FREQ_2 {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("BASE_FREQ_2");
            param.setDisplayedName("Base Freq 2");
            param.setDescription("The base frequency of the second sequence channel");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    BASE_FREQ_3 {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("BASE_FREQ_3");
            param.setDisplayedName("Base Freq 3");
            param.setDescription("The base frequency of the third sequence channel");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    BASE_FREQ_4 {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("BASE_FREQ_4");
            param.setDisplayedName("Base Freq 4");
            param.setDescription("The base frequency of the fourth sequence channel");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    DIGITAL_FILTER_REMOVED {
        Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("DIGITAL_FILTER_REMOVED");
            param.setDisplayedName("Digital filter removed");
            param.setDescription("Data shift due to the digital filter are removed");
            param.setLockedToDefault(true);
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setValue(true);
            param.setDefaultValue(true);
            return param;
        }
    },

    DIGITAL_FILTER_SHIFT {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("DIGITAL_FILTER_SHIFT");
            param.setDisplayedName("Digital filter shift");
            param.setDescription("DIGITAL_FILTER_SHIFT.description");
            param.setLocked(true);
            param.setLockedToDefault(true);
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Integer);
            param.setMinValue(0);
            param.setMaxValue(50);
            param.setValue(20);
            param.setDefaultValue(20);
            return param;
        }
    },

    DUMMY_SCAN {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("DUMMY_SCAN");
            param.setDisplayedName("DS");
            param.setDescription("Dummy Scan");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(0);
            param.setDefaultValue(128);
            return param;
        }
    },

    ECHO_TIME {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("ECHO_TIME");
            param.setDisplayedName("TE");
            param.setDescription("The echo time");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(6.0E-4);
            param.setDefaultValue(0.005);
            return param;
        }
    },

    FIELD_OF_VIEW {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("FIELD_OF_VIEW");
            param.setDisplayedName("FOV");
            param.setDescription("The field of view");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Length);
            param.setMinValue(0.0);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.06);
            param.setDefaultValue(0.6);
            return param;
        }
    },

    FLIP_ANGLE {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("FLIP_ANGLE");
            param.setDisplayedName("Flip angle");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Phase);
            param.setMinValue(0.0);
            param.setMaxValue(360.0);
            param.setValue(90.0);
            param.setDefaultValue(30.0);
            return param;
        }
    },

    GRADIENT_AMP_SPOILER {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("GRADIENT_AMP_SPOILER");
            param.setDisplayedName("GRAD_AMP_SPOILER");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.GradAmp);
            param.setMinValue(-100.0);
            param.setMaxValue(100.0);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    GRADIENT_ENABLE_SPOILER {
        Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("GRADIENT_ENABLE_SPOILER");
            param.setDisplayedName("GRADIENT_ENABLE_SPOILER");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setValue(true);
            param.setDefaultValue(false);
            return param;
        }
    },

    GRADIENT_REFOC_TIME {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("GRADIENT_REFOC_TIME");
            param.setDisplayedName("GRADIENT_REFOC_TIME");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(1.9999999999999998E-4);
            param.setDefaultValue(1.9999999999999998E-4);
            return param;
        }
    },

    GRADIENT_RISE_TIME {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("GRADIENT_RISE_TIME");
            param.setDisplayedName("GRADIENT_RISE_TIME");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(1.5E-4);
            param.setDefaultValue(9.999999999999999E-5);
            return param;
        }
    },

    GRADIENT_SPOILER_TIME {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("GRADIENT_SPOILER_TIME");
            param.setDisplayedName("GRADIENT_SPOILER_TIME");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(1.5E-4);
            param.setDefaultValue(9.999999999999999E-5);
            return param;
        }
    },

    IMAGE_ORIENTATION_SUBJECT {
        Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("IMAGE_ORIENTATION_SUBJECT");
            param.setDisplayedName("IMAGE_ORIENTATION_SUBJECT");
            param.setDescription("Direction cosines of the first row and the first column with respect to the subject");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setNumberEnum(NumberEnum.Double);
            param.setValue(asList(1.0, 0.0, 0.0, 0.0, -1.0, 0.0));
            param.setDefaultValue(asList(1.0, 0.0, 0.0, 0.0, 1.0, 0.0));
            return param;
        }
    },

    IMAGE_POSITION_SUBJECT {
        Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("IMAGE_POSITION_SUBJECT");
            param.setDisplayedName("IMAGE_POSITION_SUBJECT");
            param.setDescription("x, y, and z coordinates of the upper left hand corner of the image");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setNumberEnum(NumberEnum.Location);
            param.setValue(asList(0.0, 0.0, 0.0));
            param.setDefaultValue(asList(0.0, 0.0, 0.0));
            return param;
        }
    },

    INTERMEDIATE_FREQUENCY {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("INTERMEDIATE_FREQUENCY");
            param.setDisplayedName("INTERMEDIATE_FREQUENCY.name");
            param.setDescription("INTERMEDIATE_FREQUENCY.description");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(2.051E7);
            param.setDefaultValue(2.051E7);
            return param;
        }
    },

    LAST_PUT {
        Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("LAST_PUT");
            param.setDisplayedName("LAST_PUT");
            param.setDescription("LAST_PUT.description");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setMinValue(-2147483648);
            param.setMaxValue(2147483647);
            param.setNumberEnum(NumberEnum.Integer);
            param.setValue(asList(0, 0, 0));
            param.setDefaultValue(asList(0, 0, 0));
            return param;
        }
    },

    MAGNETIC_FIELD_STRENGTH {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("MAGNETIC_FIELD_STRENGTH");
            param.setDisplayedName("B0");
            param.setDescription("The magnetic field tregth");
            param.setLocked(true);
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Integer);
            param.setMinValue(-2147483648);
            param.setMaxValue(2147483647);
            param.setValue(5);
            param.setDefaultValue(5);
            return param;
        }
    },

    MANUFACTURER {
        Param build() {
            TextParam param = new TextParam();
            param.setName("MANUFACTURER");
            param.setDisplayedName("MANUFACTURER");
            param.setDescription("Manufacturer");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Miscellaneous);
            param.setValue("Manufacturer");
            param.setDefaultValue("Manufacturer");
            return param;
        }
    },

    MIN_RISE_TIME_FACTOR {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("MIN_RISE_TIME_FACTOR");
            param.setDisplayedName("MIN_RISE_TIME_FACTOR");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.PERCENT);
            param.setMinValue(5.0);
            param.setMaxValue(100.0);
            param.setValue(90.0);
            param.setDefaultValue(90.0);
            return param;
        }
    },

    MODALITY {
        Param build() {
            TextParam param = new TextParam();
            param.setName("MODALITY");
            param.setDisplayedName("Modality");
            param.setDescription("The modality for the acquisition");
            param.setLocked(true);
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setValue("MRI");
            param.setDefaultValue("MRI");
            param.setSuggestedValues(asList("NMR", "MRI", "DEFAULT"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    MODEL_NAME {
        Param build() {
            TextParam param = new TextParam();
            param.setName("MODEL_NAME");
            param.setDisplayedName("MODEL_NAME");
            param.setDescription("Model name");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Miscellaneous);
            param.setValue("Nanoscan MR 3T");
            param.setDefaultValue("Model name");
            return param;
        }
    },

    MULTI_PLANAR_EXCITATION {
        Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("MULTI_PLANAR_EXCITATION");
            param.setDisplayedName("MULTI_PLANAR_EXCITATION");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setValue(false);
            param.setDefaultValue(true);
            return param;
        }
    },

    NUCLEUS_1 {
        Param build() {
            TextParam param = new TextParam();
            param.setName("NUCLEUS_1");
            param.setDisplayedName("Nucleus 1");
            param.setDescription("The nucleus used for the first sequence channel");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setValue("1H");
            param.setDefaultValue("1H");
            param.setSuggestedValues(asList("1H", "13C", "15N", "2H", "31P", "35Cl", "23Na", "19F"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    NUCLEUS_2 {
        Param build() {
            TextParam param = new TextParam();
            param.setName("NUCLEUS_2");
            param.setDisplayedName("Nucleus 2");
            param.setDescription("The nucleus used for the second sequence channel");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setValue("1H");
            param.setDefaultValue("1H");
            param.setSuggestedValues(asList("1H", "13C", "15N", "2H", "31P", "35Cl", "23Na", "19F"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    NUCLEUS_3 {
        Param build() {
            TextParam param = new TextParam();
            param.setName("NUCLEUS_3");
            param.setDisplayedName("Nucleus 3");
            param.setDescription("The nucleus used for the third sequence channel");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setValue("1H");
            param.setDefaultValue("1H");
            param.setSuggestedValues(asList("1H", "13C", "15N", "2H", "31P", "35Cl", "23Na", "19F"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    NUCLEUS_4 {
        Param build() {
            TextParam param = new TextParam();
            param.setName("NUCLEUS_4");
            param.setDisplayedName("Nucleus 4");
            param.setDescription("The nucleus used for the fourth sequence channel");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setValue("1H");
            param.setDefaultValue("1H");
            param.setSuggestedValues(asList("1H", "13C", "15N", "2H", "31P", "35Cl", "23Na", "19F"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    NUMBER_OF_AVERAGES {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("NUMBER_OF_AVERAGES");
            param.setDisplayedName("NEX");
            param.setDescription("The number of average");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    },

    OBSERVED_FREQUENCY {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("OBSERVED_FREQUENCY");
            param.setDisplayedName("Observed frequency");
            param.setDescription("The frequency of the acquisition");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(1.2818100000000001E8);
            param.setDefaultValue(6.3E7);
            return param;
        }
    },

    OBSERVED_NUCLEUS {
        Param build() {
            TextParam param = new TextParam();
            param.setName("OBSERVED_NUCLEUS");
            param.setDisplayedName("Observed Nucleus");
            param.setDescription("The observed nucleus");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setValue("1H");
            param.setDefaultValue("1H");
            param.setSuggestedValues(asList("1H", "13C", "15N", "2H", "31P", "35Cl", "23Na", "19F"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_1D {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_1D");
            param.setDisplayedName("Location X");
            param.setDescription("Location in the R/L direction");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Location);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_2D {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_2D");
            param.setDisplayedName("Location Y");
            param.setDescription("Location in the A/P direction");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Location);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_3D {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_3D");
            param.setDisplayedName("Location Z");
            param.setDescription("Location in the I/S direction");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Location);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_EFF {
        Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_EFF");
            param.setDisplayedName("OFF_CENTER_FIELD_OF_VIEW_EFF");
            param.setDescription("Offcenter effective in 1D 2D and 3D (read phase slice)");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setNumberEnum(NumberEnum.Location);
            param.setValue(asList(0.0, 0.0, 0.0));
            param.setDefaultValue(asList(0.0, 0.0, 0.0));
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_X {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_X");
            param.setDisplayedName("Location X");
            param.setDescription("Location in the R/L direction");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Location);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_Y {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_Y");
            param.setDisplayedName("Location Y");
            param.setDescription("Location in the A/P direction");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Location);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_Z {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_Z");
            param.setDisplayedName("Location Z");
            param.setDescription("Location in the I/S direction");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Location);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFFSET_FREQ_1 {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFFSET_FREQ_1");
            param.setDisplayedName("Offset 1");
            param.setDescription("The offset frequency of the first sequence channel");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.FrequencyOffset);
            param.setMinValue(-1.5E9);
            param.setMaxValue(1.5E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFFSET_FREQ_2 {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFFSET_FREQ_2");
            param.setDisplayedName("Offset 2");
            param.setDescription("The offset frequency of the second sequence channel");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.FrequencyOffset);
            param.setMinValue(-1.5E9);
            param.setMaxValue(1.5E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFFSET_FREQ_3 {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFFSET_FREQ_3");
            param.setDisplayedName("Offset 3");
            param.setDescription("The offset frequency of the third sequence channel");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.FrequencyOffset);
            param.setMinValue(-1.5E9);
            param.setMaxValue(1.5E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFFSET_FREQ_4 {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFFSET_FREQ_4");
            param.setDisplayedName("Offset 4");
            param.setDescription("The offset frequency of the fourth sequence channel");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.FrequencyOffset);
            param.setMinValue(-1.5E9);
            param.setMaxValue(1.5E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    ORIENTATION {
        Param build() {
            TextParam param = new TextParam();
            param.setName("ORIENTATION");
            param.setDisplayedName("Orientation");
            param.setDescription("Field of view orientation");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setValue("AXIAL");
            param.setDefaultValue("AXIAL");
            param.setSuggestedValues(asList("AXIAL", "CORONAL", "SAGITTAL", "OBLIQUE"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    PAROPT_PARAM {
        Param build() {
            TextParam param = new TextParam();
            param.setName("PAROPT_PARAM");
            param.setDisplayedName("Parameter optimised");
            param.setDescription("Name of the current optimised parameter");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setCategory(Category.Acquisition);
            param.setValue("");
            param.setDefaultValue("PULSE_LENGTH");
            return param;
        }
    },

    PHASE_0 {
        Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("PHASE_0");
            param.setDisplayedName("PHASE_0");
            param.setDescription("PHASE_0.description");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Process);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setNumberEnum(NumberEnum.Double);
            return param;
        }
    },

    PHASE_1 {
        Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("PHASE_1");
            param.setDisplayedName("PHASE_1");
            param.setDescription("PHASE_1.description");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Process);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setNumberEnum(NumberEnum.Double);
            return param;
        }
    },

    PHASE_APPLIED {
        Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("PHASE_APPLIED");
            param.setDisplayedName("PHASE_APPLIED");
            param.setDescription("PHASE_APPLIED");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Process);
            param.setValue(false);
            param.setDefaultValue(false);
            return param;
        }
    },

    PROBE {
        Param build() {
            TextParam param = new TextParam();
            param.setName("PROBE");
            param.setDisplayedName("Probe");
            param.setDescription("The probe used for the mr acquisition");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setValue("");
            param.setDefaultValue("");
            return param;
        }
    },

    RECEIVER_COUNT {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("RECEIVER_COUNT");
            param.setDisplayedName("Receiver Count");
            param.setDescription("The number of receivers");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Integer);
            param.setMinValue(1);
            param.setMaxValue(32);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    },

    RECEIVER_GAIN {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("RECEIVER_GAIN");
            param.setDisplayedName("RG");
            param.setDescription("The receiver gain");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.RxGain);
            param.setMinValue(0.0);
            param.setMaxValue(120.0);
            param.setValue(1.0);
            param.setDefaultValue(1.0);
            return param;
        }
    },

    REPETITION_TIME {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("REPETITION_TIME");
            param.setDisplayedName("TR");
            param.setDescription("The repetition time");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(0.7000000000000001);
            param.setDefaultValue(0.2);
            return param;
        }
    },

    SEQUENCE_NAME {
        Param build() {
            TextParam param = new TextParam();
            param.setName("SEQUENCE_NAME");
            param.setDisplayedName("Seq");
            param.setDescription("The name of the sequence");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setValue("OnePulse_Slc");
            param.setDefaultValue("OnePulse_Slc");
            return param;
        }
    },

    SEQUENCE_TIME {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("SEQUENCE_TIME");
            param.setDisplayedName("SEQUENCE_TIME");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    SEQUENCE_VERSION {
        Param build() {
            TextParam param = new TextParam();
            param.setName("SEQUENCE_VERSION");
            param.setDisplayedName("SEQUENCE_VERSION");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.User);
            param.setCategory(Category.Acquisition);
            param.setValue(" Version5.3");
            param.setDefaultValue("");
            return param;
        }
    },

    SETUP_MODE {
        Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("SETUP_MODE");
            param.setDisplayedName("Setup");
            param.setDescription("True during setup process");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setValue(false);
            param.setDefaultValue(false);
            return param;
        }
    },

    SLICE_THICKNESS {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("SLICE_THICKNESS");
            param.setDisplayedName("SLICE_THICKNESS");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Length);
            param.setMinValue(0.0);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.02);
            param.setDefaultValue(0.005);
            return param;
        }
    },

    SOFTWARE_VERSION {
        Param build() {
            TextParam param = new TextParam();
            param.setName("SOFTWARE_VERSION");
            param.setDisplayedName("SOFTWARE_VERSION");
            param.setDescription("The version of the software");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Miscellaneous);
            param.setValue("Software version");
            param.setDefaultValue("Software version");
            return param;
        }
    },

    SPECTRAL_WIDTH {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("SPECTRAL_WIDTH");
            param.setDisplayedName("SW");
            param.setDescription("The spectral width of the reception");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.SW);
            param.setMinValue(119.3);
            param.setMaxValue(3906000.0);
            param.setValue(20032.05128205128);
            param.setDefaultValue(12520.0);
            return param;
        }
    },

    SPECTRAL_WIDTH_OPT {
        Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("SPECTRAL_WIDTH_OPT");
            param.setDisplayedName("SPECTRAL_WIDTH_OPT");
            param.setDescription("Use SW to calculate SW_PER_PIXEL (true) Use SW_PER_PIXEL to calculate SW (false)");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setValue(true);
            param.setDefaultValue(false);
            return param;
        }
    },

    SPECTRAL_WIDTH_PER_PIXEL {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("SPECTRAL_WIDTH_PER_PIXEL");
            param.setDisplayedName("SPECTRAL_WIDTH_PER_PIXEL");
            param.setDescription("Spectral Width per pixel in Hz / Pix");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.SW);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E8);
            param.setValue(2.4453187600160255);
            param.setDefaultValue(100.0);
            return param;
        }
    },

    STATION_NAME {
        Param build() {
            TextParam param = new TextParam();
            param.setName("STATION_NAME");
            param.setDisplayedName("STATION_NAME");
            param.setDescription("Station name");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Miscellaneous);
            param.setValue("Station name");
            param.setDefaultValue("Station name");
            return param;
        }
    },

    SUBJECT_POSITION {
        Param build() {
            TextParam param = new TextParam();
            param.setName("SUBJECT_POSITION");
            param.setDisplayedName("SUBJECT_POSITION");
            param.setDescription("Subject position relative to the magnet.");
            param.setLocked(true);
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setValue("HeadFirstProne");
            param.setDefaultValue("FeetFirstProne");
            param.setSuggestedValues(asList("HeadFirstProne", "HeadFirstSupine", "FeetFirstProne", "FeetFirstSupine"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    TRANSFORM_PLUGIN {
        Param build() {
            TextParam param = new TextParam();
            param.setName("TRANSFORM_PLUGIN");
            param.setDisplayedName("Transform plugin");
            param.setDescription("Transform the acquisition space to the k space");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setValue("none");
            param.setDefaultValue("none");
            param.setSuggestedValues(asList("Bordered2D", "Bordered2D_FSE", "Centered2D", "Centered2D_FSE", "Sequential2D", "Sequential2D_EPI", "Sequential2D_EPI_Cal", "Sequential2D_EPI_Int", "Sequential4D", "Sequential4DBackAndForth", "Sequential4DCine"));
            return param;
        }
    },

    TRIGGER_CHANEL {
        Param build() {
            TextParam param = new TextParam();
            param.setName("TRIGGER_CHANEL");
            param.setDisplayedName("Trigger Channel");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setValue("Ext1_XOR_Ext2");
            param.setDefaultValue("Ext1_XOR_Ext2");
            param.setSuggestedValues(asList("Ext1", "Ext2", "Ext1_AND_Ext2", "Ext1_XOR_Ext2"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    TRIGGER_EXTERNAL {
        Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("TRIGGER_EXTERNAL");
            param.setDisplayedName("TRIGGER_EXTERNAL");
            param.setDescription("Enable the synchronization of the sequence with the trigger");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setValue(false);
            param.setDefaultValue(false);
            return param;
        }
    },

    TRIGGER_TIME {
        Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("TRIGGER_TIME");
            param.setDisplayedName("TRIGGER_TIME");
            param.setDescription("TRIGGER_TIME.description");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setNumberEnum(NumberEnum.Time);
            param.setValue(asList(0.0, 0.1, 0.2, 0.3));
            param.setDefaultValue(asList(0.01));
            return param;
        }
    },

    TX_AMP {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_AMP");
            param.setDisplayedName("TX_AMP");
            param.setDescription("The emission amplitude");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.PERCENT);
            param.setMinValue(0.0);
            param.setMaxValue(100.0);
            param.setValue(40.087732488176094);
            param.setDefaultValue(40.0);
            return param;
        }
    },

    TX_AMP_ATT_AUTO {
        Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("TX_AMP_ATT_AUTO");
            param.setDisplayedName("TX_AMP_ATT_AUTO");
            param.setDescription("Use the ATT and AMP set from the calibration ");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setCategory(Category.Acquisition);
            param.setValue(false);
            param.setDefaultValue(true);
            return param;
        }
    },

    TX_AMP_NUMBER {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_AMP_NUMBER");
            param.setDisplayedName("TX_AMP_NUMBER");
            param.setDescription("number of step");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Integer);
            param.setMinValue(1);
            param.setMaxValue(2048);
            param.setValue(15);
            param.setDefaultValue(1);
            return param;
        }
    },

    TX_AMP_START {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_AMP_START");
            param.setDisplayedName("TX_AMP_START");
            param.setDescription("Starting value of the Amp ramp");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Double);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(5.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    TX_AMP_STEP {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_AMP_STEP");
            param.setDisplayedName("TX_AMP_STEP");
            param.setDescription("Step between two amp");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.TxAmp);
            param.setMinValue(0.0);
            param.setMaxValue(100.0);
            param.setValue(1.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    TX_ATT {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_ATT");
            param.setDisplayedName("TX_ATT");
            param.setDescription("The emission attenuation");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.TxAtt);
            param.setMinValue(0.0);
            param.setMaxValue(63.0);
            param.setValue(40.0);
            param.setDefaultValue(36.0);
            return param;
        }
    },

    TX_LENGTH {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_LENGTH");
            param.setDisplayedName("TX_LENGTH");
            param.setDescription("RF time duration");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0);
            param.setValue(0.001);
            param.setDefaultValue(0.3);
            return param;
        }
    },

    TX_NUTATION {
        Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("TX_NUTATION");
            param.setDisplayedName("TX_NUTATION");
            param.setDescription("allows to acquire nutation curve");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setValue(false);
            param.setDefaultValue(false);
            return param;
        }
    },

    TX_POWER {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_POWER");
            param.setDisplayedName("TX_POWER");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Power);
            param.setMinValue(0.0);
            param.setMaxValue(10000.0);
            param.setValue(0.010206163258074037);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    TX_ROUTE {
        Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("TX_ROUTE");
            param.setDisplayedName("TX route");
            param.setDescription("LogCh->PhysCh");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setMinValue(-2147483648);
            param.setMaxValue(2147483647);
            param.setNumberEnum(NumberEnum.Integer);
            param.setValue(asList(0));
            return param;
        }
    },

    TX_SHAPE {
        Param build() {
            TextParam param = new TextParam();
            param.setName("TX_SHAPE");
            param.setDisplayedName("TX_SHAPE");
            param.setDescription("");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setCategory(Category.Acquisition);
            param.setValue("GAUSSIAN");
            param.setDefaultValue("GAUSSIAN");
            param.setSuggestedValues(asList("GAUSSIAN", "SINC3", "HARD", "SINC5"));
            return param;
        }
    },

    USER_MATRIX_DIMENSION_1D {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("USER_MATRIX_DIMENSION_1D");
            param.setDisplayedName("Matrix 1D");
            param.setDescription("The matrix size of the first dimension");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(8192);
            param.setDefaultValue(128);
            return param;
        }
    },

    USER_MATRIX_DIMENSION_2D {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("USER_MATRIX_DIMENSION_2D");
            param.setDisplayedName("Matrix 2D");
            param.setDescription("The matrix size of the second dimension");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(1);
            param.setDefaultValue(128);
            return param;
        }
    },

    USER_MATRIX_DIMENSION_3D {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("USER_MATRIX_DIMENSION_3D");
            param.setDisplayedName("Matrix 3D");
            param.setDescription("The matrix size of the third dimension");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    },

    USER_MATRIX_DIMENSION_4D {
        Param build() {
            NumberParam param = new NumberParam();
            param.setName("USER_MATRIX_DIMENSION_4D");
            param.setDisplayedName("Matrix 4D");
            param.setDescription("The matrix size of the fourth dimension");
            param.setRoles(new RoleEnum[] {RoleEnum.User});
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    };

    abstract Param build();
}