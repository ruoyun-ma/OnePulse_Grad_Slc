//-- generated code, will be overwritten at each recompilation

package rs2d.sequence.onpulseslc;

import rs2d.spinlab.tools.param.*;
import rs2d.spinlab.tools.table.*;
import rs2d.spinlab.sequenceGenerator.GeneratorParamEnum;

import java.util.List;
import static java.util.Arrays.asList;

public enum U implements GeneratorParamEnum {
    ACCU_DIM("ACCU_DIM") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("ACCU_DIM");
            param.setDisplayedName("ACCU_DIM");
            param.setDescription("Dimension on which averaging is performed by the Cameleon");
            param.setLocked(true);
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setUuid("9cd6e783-f1ec-4ec8-8733-a0a99f1bc792");
            param.setNumberEnum(NumberEnum.Integer);
            param.setMinValue(0);
            param.setMaxValue(3);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    },

    ACQUISITION_MATRIX_DIMENSION_1D("ACQUISITION_MATRIX_DIMENSION_1D") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("ACQUISITION_MATRIX_DIMENSION_1D");
            param.setDisplayedName("Acquisition Matrix 1D");
            param.setDescription("Info: Size of the initial dataset (raw data) in first dimension");
            param.setLocked(true);
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setUuid("0798747f-d14e-4aa0-afdf-b4fd7aaf95d4");
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(8192);
            param.setDefaultValue(128);
            return param;
        }
    },

    ACQUISITION_MATRIX_DIMENSION_2D("ACQUISITION_MATRIX_DIMENSION_2D") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("ACQUISITION_MATRIX_DIMENSION_2D");
            param.setDisplayedName("Acquisition Matrix 2D");
            param.setDescription("Info: Size of the initial dataset (raw data) in the second dimension");
            param.setLocked(true);
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setUuid("6f83f8fd-a03b-47d3-bc26-4728d4b798be");
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(11);
            param.setDefaultValue(128);
            return param;
        }
    },

    ACQUISITION_MATRIX_DIMENSION_3D("ACQUISITION_MATRIX_DIMENSION_3D") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("ACQUISITION_MATRIX_DIMENSION_3D");
            param.setDisplayedName("Acquisition Matrix 3D");
            param.setDescription("Info: Size of the initial dataset (raw data) in the third dimension");
            param.setLocked(true);
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setUuid("856f1277-41a4-433b-adbc-86dc3e5a0d13");
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    },

    ACQUISITION_MATRIX_DIMENSION_4D("ACQUISITION_MATRIX_DIMENSION_4D") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("ACQUISITION_MATRIX_DIMENSION_4D");
            param.setDisplayedName("Acquisition Matrix 4D");
            param.setDescription("Info: Size of raw data in fourth dimension");
            param.setLocked(true);
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setUuid("1137b850-50c9-40fe-a531-5881e3c1f39b");
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    },

    ACQUISITION_MODE("ACQUISITION_MODE") {
        public Param build() {
            ListTextParam param = new ListTextParam();
            param.setName("ACQUISITION_MODE");
            param.setDisplayedName("ACQUISITION_MODE");
            param.setDescription("ACQUISITION_MODE and DATA_REPRESENTATION are filled according to the phase modulation chosen");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setUuid("2b8082e1-2500-43f1-97e2-530d4c7623a4");
            param.setValue(asList("COMPLEX", "REAL", "REAL", "REAL"));
            param.setDefaultValue(asList("COMPLEX", "REAL", "REAL", "REAL"));
            return param;
        }
    },

    ACQUISITION_TIME_PER_SCAN("ACQUISITION_TIME_PER_SCAN") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("ACQUISITION_TIME_PER_SCAN");
            param.setDisplayedName("Observation Time");
            param.setDescription("Info: Time during which the signal is sampled by the ADC");
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("4a00929e-3633-489a-a4ca-1597f447876f");
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(0.40894464);
            param.setDefaultValue(1.0);
            return param;
        }
    },

    ADJUST_WINDOW("ADJUST_WINDOW") {
        public Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("ADJUST_WINDOW");
            param.setDisplayedName("Adjust Window");
            param.setDescription("Enable to get the frequency from 'Base Frequency 1' and not from instrument ");
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("4e02b8a6-e0da-4420-80e0-a9dbf4031c79");
            param.setValue(false);
            param.setDefaultValue(false);
            return param;
        }
    },

    BASE_FREQ_1("BASE_FREQ_1") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("BASE_FREQ_1");
            param.setDisplayedName("Base Frequency 1");
            param.setDescription("Info: Frequency of the observed nucleus");
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("bdb638b1-d07c-4cd7-8720-40b53133ab4f");
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(6.38067934119089E7);
            param.setDefaultValue(1.27194553E8);
            return param;
        }
    },

    BASE_FREQ_2("BASE_FREQ_2") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("BASE_FREQ_2");
            param.setDisplayedName("Base Frequency 2");
            param.setDescription("Info: Base frequency of the second channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("7ca8c56e-62fa-41b8-9de3-ab877593fb34");
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    BASE_FREQ_3("BASE_FREQ_3") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("BASE_FREQ_3");
            param.setDisplayedName("Base Frequency 3");
            param.setDescription("Info: Base frequency of the third channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("3cb8b8e6-5603-49ba-87e2-09d7a26cdfcb");
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    BASE_FREQ_4("BASE_FREQ_4") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("BASE_FREQ_4");
            param.setDisplayedName("Base Frequency 4");
            param.setDescription("Info: Base frequency of the fourth channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("08091c19-7724-45c5-a00c-c0aba8921519");
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    DATA_REPRESENTATION("DATA_REPRESENTATION") {
        public Param build() {
            ListTextParam param = new ListTextParam();
            param.setName("DATA_REPRESENTATION");
            param.setDisplayedName("DATA_REPRESENTATION");
            param.setDescription("ACQUISITION_MODE and DATA_REPRESENTATION are filled according to the phase modulation chosen");
            param.setLocked(true);
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Miscellaneous);
            param.setUuid("c0ed7070-9ef3-4c4c-aa17-4ce082984d23");
            param.setValue(asList("COMPLEX", "REAL", "REAL", "REAL"));
            param.setDefaultValue(asList("COMPLEX", "REAL", "REAL", "REAL"));
            return param;
        }
    },

    DEBUG_MODE("DEBUG_MODE") {
        public Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("DEBUG_MODE");
            param.setDisplayedName("DEBUG_MODE");
            param.setDescription("Print additionnal information in wommand window for debug purpose");
            param.setLocked(true);
            param.setCategory(Category.Miscellaneous);
            param.setUuid("763dd155-b08f-425e-a873-b29327cd7e87");
            param.setValue(false);
            param.setDefaultValue(false);
            return param;
        }
    },

    DELAY_ECHO("DELAY_ECHO") {
        public Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("DELAY_ECHO");
            param.setDisplayedName("Use Dead Time");
            param.setDescription("Enable to delay the signal reception by adding a 'Tx-Rx Dead Time' to the minimal 'Tx-Rx Time'");
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setUuid("3bd9c1e1-5104-435c-977e-3c1ebffbfe6e");
            param.setValue(false);
            param.setDefaultValue(false);
            return param;
        }
    },

    DELAY_ECHO_TIME("DELAY_ECHO_TIME") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("DELAY_ECHO_TIME");
            param.setDisplayedName("Tx-Rx Dead Time");
            param.setDescription("Extra time added before the signal reception (input if  'Use Dead Time' checked)");
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setUuid("6975b382-aa8f-424a-bee8-29e406cd9884");
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(0.495011);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    DIGITAL_FILTER_REMOVED("DIGITAL_FILTER_REMOVED") {
        public Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("DIGITAL_FILTER_REMOVED");
            param.setDisplayedName("DIGITAL_FILTER_REMOVED");
            param.setDescription("Enable to activate the data shift");
            param.setLockedToDefault(true);
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("99de84e0-c423-48db-b8e4-6cef04f4fd20");
            param.setValue(true);
            param.setDefaultValue(true);
            return param;
        }
    },

    DIGITAL_FILTER_SHIFT("DIGITAL_FILTER_SHIFT") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("DIGITAL_FILTER_SHIFT");
            param.setDisplayedName("DIGITAL_FILTER_SHIFT");
            param.setDescription("Data shift due to the digital filter");
            param.setLocked(true);
            param.setLockedToDefault(true);
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("484dce7e-4f45-4722-bd3e-50f7b134c76d");
            param.setNumberEnum(NumberEnum.Integer);
            param.setMinValue(0);
            param.setMaxValue(50);
            param.setValue(19);
            param.setDefaultValue(19);
            return param;
        }
    },

    DUMMY_SCAN("DUMMY_SCAN") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("DUMMY_SCAN");
            param.setDisplayedName("Dummy Scans");
            param.setDescription("Number of dummy cycles used to reach steady-state");
            param.setLocked(true);
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setUuid("f7dc05d2-66dd-4b5c-abdd-68233d6d686a");
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(0);
            param.setDefaultValue(128);
            return param;
        }
    },

    DWELL_TIME("DWELL_TIME") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("DWELL_TIME");
            param.setDisplayedName("DW");
            param.setDescription("Reception dwell time");
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("51a26fae-db89-4065-aa74-8bd11708d324");
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(4.992E-5);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    ECHO_TIME("ECHO_TIME") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("ECHO_TIME");
            param.setDisplayedName("Tx-Rx Time");
            param.setDescription("Time between the middle of the excitation RF and the beginning of the reception  (input if 'Use Dead Time' unchecked)");
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setUuid("cfd6e656-00a1-4b37-b20b-963156dc7444");
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(0.50003);
            param.setDefaultValue(0.005);
            return param;
        }
    },

    FIELD_OF_VIEW("FIELD_OF_VIEW") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("FIELD_OF_VIEW");
            param.setDisplayedName("FOV");
            param.setDescription("Field of view");
            param.setLocked(true);
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setUuid("9e7aefaf-1eb6-488e-b49e-b0a9ed23054d");
            param.setNumberEnum(NumberEnum.Length);
            param.setMinValue(0.0);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.06);
            param.setDefaultValue(0.6);
            return param;
        }
    },

    FLIP_ANGLE("FLIP_ANGLE") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("FLIP_ANGLE");
            param.setDisplayedName("Flip Angle");
            param.setDescription("Excitation flip angle");
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("84fcfef6-874c-48e8-a621-54d1ada54933");
            param.setNumberEnum(NumberEnum.PulseAngle);
            param.setMinValue(0.0);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(8874.0);
            param.setDefaultValue(30.0);
            return param;
        }
    },

    GRADIENT_REFOC_TIME("GRADIENT_REFOC_TIME") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("GRADIENT_REFOC_TIME");
            param.setDisplayedName("Gradient Slice Refoc Duration");
            param.setDescription("Top time of the slice refocusing gradient");
            param.setCategory(Category.Acquisition);
            param.setUuid("ec897c60-293c-4381-a9e5-e9e9dd23b574");
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(1.9999999999999998E-4);
            param.setDefaultValue(1.9999999999999998E-4);
            return param;
        }
    },

    GRADIENT_RISE_TIME("GRADIENT_RISE_TIME") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("GRADIENT_RISE_TIME");
            param.setDisplayedName("Gradient Rise Time");
            param.setDescription("Rise time of the gradient");
            param.setCategory(Category.Acquisition);
            param.setUuid("2a02a20c-ffc0-4b25-9700-338b38920822");
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(1.5E-4);
            param.setDefaultValue(9.999999999999999E-5);
            return param;
        }
    },

    GRADIENT_SPOILER_ACTIVATE("GRADIENT_SPOILER_ACTIVATE") {
        public Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("GRADIENT_SPOILER_ACTIVATE");
            param.setDisplayedName("Gradient Spoiler Enable");
            param.setDescription("Enable gradient spoiler");
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setUuid("ae817834-e929-44f7-aa02-2bc64b1d1f01");
            param.setValue(false);
            param.setDefaultValue(false);
            return param;
        }
    },

    GRADIENT_SPOILER_AMP("GRADIENT_SPOILER_AMP") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("GRADIENT_SPOILER_AMP");
            param.setDisplayedName("Gradient Spoiler Amplitude");
            param.setDescription("Amplitude of the spoiler gradient");
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setUuid("12ee445d-f230-4b6a-af60-83a836aa585b");
            param.setNumberEnum(NumberEnum.GradAmp);
            param.setMinValue(-100.0);
            param.setMaxValue(100.0);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    GRADIENT_SPOILER_TIME("GRADIENT_SPOILER_TIME") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("GRADIENT_SPOILER_TIME");
            param.setDisplayedName("Gradient Spoiler Duration");
            param.setDescription("Duration of the spoiler gradients");
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setUuid("378bbc96-3eff-463b-a615-73d4b0d36384");
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(1.5E-4);
            param.setDefaultValue(9.999999999999999E-5);
            return param;
        }
    },

    IMAGE_ORIENTATION_SUBJECT("IMAGE_ORIENTATION_SUBJECT") {
        public Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("IMAGE_ORIENTATION_SUBJECT");
            param.setDisplayedName("Image Orientation");
            param.setDescription("Direction cosines of the first row and the first column with respect to the subject");
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setUuid("697aeea3-4292-4d3e-b0a5-4a20f941e562");
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setNumberEnum(NumberEnum.Double);
            param.setValue(asListNumber(1.0, 0.0, 0.0, 0.0, -1.0, 0.0));
            param.setDefaultValue(asListNumber(1.0, 0.0, 0.0, 0.0, 1.0, 0.0));
            return param;
        }
    },

    INTERMEDIATE_FREQUENCY("INTERMEDIATE_FREQUENCY") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("INTERMEDIATE_FREQUENCY");
            param.setDisplayedName("INTERMEDIATE_FREQUENCY");
            param.setDescription("Info: Frequency from the Hardware used for the signal demodulation (ADC optimal frequency)");
            param.setLocked(true);
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("9fc49f7f-88e3-418f-b2af-b15f962189a7");
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(1.25E7);
            param.setDefaultValue(1.25E7);
            return param;
        }
    },

    LAST_PUT("LAST_PUT") {
        public Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("LAST_PUT");
            param.setDisplayedName("LAST_PUT");
            param.setDescription("LAST_PUT.description");
            param.setLocked(true);
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("4e8871fe-5996-403d-b0dd-fc032b29d3de");
            param.setMinValue(-2147483648);
            param.setMaxValue(2147483647);
            param.setNumberEnum(NumberEnum.Integer);
            param.setValue(asListNumber(0, 0, 0));
            param.setDefaultValue(asListNumber(0, 0, 0));
            return param;
        }
    },

    MAGNETIC_FIELD_STRENGTH("MAGNETIC_FIELD_STRENGTH") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("MAGNETIC_FIELD_STRENGTH");
            param.setDisplayedName("B0 Strength");
            param.setDescription("Info: Magnetic field stength from the hardware");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setUuid("ce975cb0-8385-4877-98c9-09a8a853c03c");
            param.setNumberEnum(NumberEnum.Integer);
            param.setMinValue(-2147483648);
            param.setMaxValue(2147483647);
            param.setValue(5);
            param.setDefaultValue(1);
            return param;
        }
    },

    MANUFACTURER("MANUFACTURER") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("MANUFACTURER");
            param.setDisplayedName("Manufacturer");
            param.setDescription("Manufacturer");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Miscellaneous);
            param.setUuid("f886f0a2-63b6-4633-a0e9-511c755bcc4c");
            param.setValue("Manufacturer");
            param.setDefaultValue("Manufacturer");
            return param;
        }
    },

    MIN_RISE_TIME_FACTOR("MIN_RISE_TIME_FACTOR") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("MIN_RISE_TIME_FACTOR");
            param.setDisplayedName("Gradient Rise Time Factor");
            param.setDescription("Safety parameter applied on maximum gradient slew rate. Fastest is 100%");
            param.setLocked(true);
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setUuid("629ec5b0-5caa-47f5-a6aa-68a3ed92afbb");
            param.setNumberEnum(NumberEnum.PERCENT);
            param.setMinValue(5.0);
            param.setMaxValue(100.0);
            param.setValue(90.0);
            param.setDefaultValue(90.0);
            return param;
        }
    },

    MODALITY("MODALITY") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("MODALITY");
            param.setDisplayedName("Modality");
            param.setDescription("Info: Acquisition modality");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setUuid("5696ae83-1167-40e1-b719-4275b9b2bac1");
            param.setValue("MRI");
            param.setDefaultValue("MRI");
            param.setSuggestedValues(asList("NMR", "MRI", "DEFAULT"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    MODEL_NAME("MODEL_NAME") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("MODEL_NAME");
            param.setDisplayedName("Model Name");
            param.setDescription("Model name");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Miscellaneous);
            param.setUuid("a3cc23ef-4d07-4275-9a2e-60529c0e6968");
            param.setValue("Nanoscan MR 3T");
            param.setDefaultValue("Model name");
            return param;
        }
    },

    MULTI_PLANAR_EXCITATION("MULTI_PLANAR_EXCITATION") {
        public Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("MULTI_PLANAR_EXCITATION");
            param.setDisplayedName("Slice Excitation");
            param.setDescription("Enable to do slice slection during RF exitation");
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setUuid("ccf3eb9b-724f-49b3-bd21-98bd3824a13c");
            param.setValue(false);
            param.setDefaultValue(true);
            return param;
        }
    },

    NUCLEUS_1("NUCLEUS_1") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("NUCLEUS_1");
            param.setDisplayedName("Nucleus 1");
            param.setDescription("Nucleus used for the first sequence channel");
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setUuid("541adef8-2df0-4304-a2f7-fac4bff551ff");
            param.setValue("1H");
            param.setDefaultValue("1H");
            param.setSuggestedValues(asList("Y", "Other", "X", "1H", "2H", "3H", "3He", "6Li", "7Li", "9Be", "10B", "11B", "13C", "14N", "15N", "17O", "19F", "21Ne", "23Na", "25Mg", "27Al", "29Si", "31P", "33S", "35Cl", "37Cl", "39K", "40K", "41K", "43Ca", "45Sc", "47Ti", "49Ti", "50V", "51V", "53Cr", "55Mn", "57Fe", "59Co", "61Ni", "63Cu", "65Cu", "67Zn", "69Ga", "71Ga", "73Ge", "75As", "77Se", "79Br", "81Br", "83Kr", "85Rb", "87Rb", "87Sr", "89Y", "91Zr", "93Nb", "95Mo", "97Mo", "99Tc", "99Ru", "101Ru", "103Rh", "105Pd", "107Ag", "109Ag", "111Cd", "113Cd", "113In", "115Sn", "115In", "117Sn", "119Sn", "121Sb", "123Te", "123Sb", "125Te", "127I", "129Xe", "131Xe", "133Cs", "135Ba", "137Ba", "138La", "139La", "141Pr", "143Nd", "145Nd", "147Sm", "149Sm", "151Eu", "153Eu", "155Gd", "157Gd", "159Tb", "161Dy", "163Dy", "165Ho", "167Er", "169Tm", "171Yb", "173Yb", "175Lu", "176Lu", "177Hf", "179Hf", "181Ta", "183W", "185Re", "187Re", "187Os", "189Os", "191Ir", "193Ir", "195Pt", "197Au", "199Hg", "201Hg", "203Tl", "205Tl", "207Pb", "209Bi", "235U"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    NUCLEUS_2("NUCLEUS_2") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("NUCLEUS_2");
            param.setDisplayedName("Nucleus 2");
            param.setDescription("Nucleus used for the second sequence channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setUuid("62a55ab1-e1a2-45ab-aab3-c84845df96ed");
            param.setValue("1H");
            param.setDefaultValue("1H");
            param.setSuggestedValues(asList("Y", "Other", "X", "1H", "2H", "3H", "3He", "6Li", "7Li", "9Be", "10B", "11B", "13C", "14N", "15N", "17O", "19F", "21Ne", "23Na", "25Mg", "27Al", "29Si", "31P", "33S", "35Cl", "37Cl", "39K", "40K", "41K", "43Ca", "45Sc", "47Ti", "49Ti", "50V", "51V", "53Cr", "55Mn", "57Fe", "59Co", "61Ni", "63Cu", "65Cu", "67Zn", "69Ga", "71Ga", "73Ge", "75As", "77Se", "79Br", "81Br", "83Kr", "85Rb", "87Rb", "87Sr", "89Y", "91Zr", "93Nb", "95Mo", "97Mo", "99Tc", "99Ru", "101Ru", "103Rh", "105Pd", "107Ag", "109Ag", "111Cd", "113Cd", "113In", "115Sn", "115In", "117Sn", "119Sn", "121Sb", "123Te", "123Sb", "125Te", "127I", "129Xe", "131Xe", "133Cs", "135Ba", "137Ba", "138La", "139La", "141Pr", "143Nd", "145Nd", "147Sm", "149Sm", "151Eu", "153Eu", "155Gd", "157Gd", "159Tb", "161Dy", "163Dy", "165Ho", "167Er", "169Tm", "171Yb", "173Yb", "175Lu", "176Lu", "177Hf", "179Hf", "181Ta", "183W", "185Re", "187Re", "187Os", "189Os", "191Ir", "193Ir", "195Pt", "197Au", "199Hg", "201Hg", "203Tl", "205Tl", "207Pb", "209Bi", "235U"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    NUCLEUS_3("NUCLEUS_3") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("NUCLEUS_3");
            param.setDisplayedName("Nucleus 3");
            param.setDescription("Nucleus used for the third sequence channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setUuid("0cb9423f-345f-4f04-b852-015f2c21e42a");
            param.setValue("1H");
            param.setDefaultValue("1H");
            param.setSuggestedValues(asList("Y", "Other", "X", "1H", "2H", "3H", "3He", "6Li", "7Li", "9Be", "10B", "11B", "13C", "14N", "15N", "17O", "19F", "21Ne", "23Na", "25Mg", "27Al", "29Si", "31P", "33S", "35Cl", "37Cl", "39K", "40K", "41K", "43Ca", "45Sc", "47Ti", "49Ti", "50V", "51V", "53Cr", "55Mn", "57Fe", "59Co", "61Ni", "63Cu", "65Cu", "67Zn", "69Ga", "71Ga", "73Ge", "75As", "77Se", "79Br", "81Br", "83Kr", "85Rb", "87Rb", "87Sr", "89Y", "91Zr", "93Nb", "95Mo", "97Mo", "99Tc", "99Ru", "101Ru", "103Rh", "105Pd", "107Ag", "109Ag", "111Cd", "113Cd", "113In", "115Sn", "115In", "117Sn", "119Sn", "121Sb", "123Te", "123Sb", "125Te", "127I", "129Xe", "131Xe", "133Cs", "135Ba", "137Ba", "138La", "139La", "141Pr", "143Nd", "145Nd", "147Sm", "149Sm", "151Eu", "153Eu", "155Gd", "157Gd", "159Tb", "161Dy", "163Dy", "165Ho", "167Er", "169Tm", "171Yb", "173Yb", "175Lu", "176Lu", "177Hf", "179Hf", "181Ta", "183W", "185Re", "187Re", "187Os", "189Os", "191Ir", "193Ir", "195Pt", "197Au", "199Hg", "201Hg", "203Tl", "205Tl", "207Pb", "209Bi", "235U"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    NUCLEUS_4("NUCLEUS_4") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("NUCLEUS_4");
            param.setDisplayedName("Nucleus 4");
            param.setDescription("Nucleus used for the fourth sequence channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setUuid("e8cb68ca-c069-4d1f-a36e-bd02d024a57f");
            param.setValue("1H");
            param.setDefaultValue("1H");
            param.setSuggestedValues(asList("Y", "Other", "X", "1H", "2H", "3H", "3He", "6Li", "7Li", "9Be", "10B", "11B", "13C", "14N", "15N", "17O", "19F", "21Ne", "23Na", "25Mg", "27Al", "29Si", "31P", "33S", "35Cl", "37Cl", "39K", "40K", "41K", "43Ca", "45Sc", "47Ti", "49Ti", "50V", "51V", "53Cr", "55Mn", "57Fe", "59Co", "61Ni", "63Cu", "65Cu", "67Zn", "69Ga", "71Ga", "73Ge", "75As", "77Se", "79Br", "81Br", "83Kr", "85Rb", "87Rb", "87Sr", "89Y", "91Zr", "93Nb", "95Mo", "97Mo", "99Tc", "99Ru", "101Ru", "103Rh", "105Pd", "107Ag", "109Ag", "111Cd", "113Cd", "113In", "115Sn", "115In", "117Sn", "119Sn", "121Sb", "123Te", "123Sb", "125Te", "127I", "129Xe", "131Xe", "133Cs", "135Ba", "137Ba", "138La", "139La", "141Pr", "143Nd", "145Nd", "147Sm", "149Sm", "151Eu", "153Eu", "155Gd", "157Gd", "159Tb", "161Dy", "163Dy", "165Ho", "167Er", "169Tm", "171Yb", "173Yb", "175Lu", "176Lu", "177Hf", "179Hf", "181Ta", "183W", "185Re", "187Re", "187Os", "189Os", "191Ir", "193Ir", "195Pt", "197Au", "199Hg", "201Hg", "203Tl", "205Tl", "207Pb", "209Bi", "235U"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    NUMBER_OF_AVERAGES("NUMBER_OF_AVERAGES") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("NUMBER_OF_AVERAGES");
            param.setDisplayedName("Nex");
            param.setDescription("Number of average");
            param.setLocked(true);
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setUuid("4112f1d5-1da2-4fc8-9801-ca5a3681e4f4");
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    },

    OBSERVED_FREQUENCY("OBSERVED_FREQUENCY") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("OBSERVED_FREQUENCY");
            param.setDisplayedName("Observed Frequency");
            param.setDescription("Info: Frequency of the acquisition");
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("d93d76ff-b4cc-42bd-aa43-dfa44cc9e28d");
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(6.38067934119089E7);
            param.setDefaultValue(6.3E7);
            return param;
        }
    },

    OBSERVED_NUCLEUS("OBSERVED_NUCLEUS") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("OBSERVED_NUCLEUS");
            param.setDisplayedName("Observed Nucleus");
            param.setDescription("Info: Observed nucleus");
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("07bdc4b7-4169-46da-a597-84ab4f3e40c8");
            param.setValue("1H");
            param.setDefaultValue("1H");
            param.setSuggestedValues(asList("1H", "13C", "15N", "2H", "31P", "35Cl", "23Na", "19F"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    OFFSET_FREQ_1("OFFSET_FREQ_1") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFFSET_FREQ_1");
            param.setDisplayedName("Frequency Offset 1");
            param.setDescription("Frequency offset of the first channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("78b10faf-8b44-4bc4-be82-29e24362f840");
            param.setNumberEnum(NumberEnum.FrequencyOffset);
            param.setMinValue(-1.5E9);
            param.setMaxValue(1.5E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFFSET_FREQ_2("OFFSET_FREQ_2") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFFSET_FREQ_2");
            param.setDisplayedName("Frequency Offset 2");
            param.setDescription("Frequency offset of the second channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("31c94663-46a1-409c-9aa4-3e932937cef4");
            param.setNumberEnum(NumberEnum.FrequencyOffset);
            param.setMinValue(-1.5E9);
            param.setMaxValue(1.5E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFFSET_FREQ_3("OFFSET_FREQ_3") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFFSET_FREQ_3");
            param.setDisplayedName("Frequency Offset 3");
            param.setDescription("Frequency offset of the third channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("bf23a0de-793d-4084-8e07-b8867bf67245");
            param.setNumberEnum(NumberEnum.FrequencyOffset);
            param.setMinValue(-1.5E9);
            param.setMaxValue(1.5E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFFSET_FREQ_4("OFFSET_FREQ_4") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFFSET_FREQ_4");
            param.setDisplayedName("Frequency Offset 4");
            param.setDescription("Frequency offset of the fourth channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("8d9eab58-fa42-43e2-bcbb-9e10f715936c");
            param.setNumberEnum(NumberEnum.FrequencyOffset);
            param.setMinValue(-1.5E9);
            param.setMaxValue(1.5E9);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_1D("OFF_CENTER_FIELD_OF_VIEW_1D") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_1D");
            param.setDisplayedName("Location 1D");
            param.setDescription("Info: Off-center distance in Readout Direction");
            param.setLocked(true);
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setUuid("19e5fd79-78f0-4b6c-b425-66473429ef79");
            param.setNumberEnum(NumberEnum.Location);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_2D("OFF_CENTER_FIELD_OF_VIEW_2D") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_2D");
            param.setDisplayedName("Location 2D");
            param.setDescription("Info: Off-center distance in Phase Encoding Direction");
            param.setLocked(true);
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setUuid("6458b638-fc79-4283-a536-ab823e9f92e2");
            param.setNumberEnum(NumberEnum.Location);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_3D("OFF_CENTER_FIELD_OF_VIEW_3D") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_3D");
            param.setDisplayedName("Location 3D");
            param.setDescription("Info: Off-center distance in Slice Direction");
            param.setLocked(true);
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setUuid("323fcaac-e8b8-419a-8213-eda25fc00b1f");
            param.setNumberEnum(NumberEnum.Location);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_EFF("OFF_CENTER_FIELD_OF_VIEW_EFF") {
        public Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_EFF");
            param.setDisplayedName("Location Eff");
            param.setDescription("Info: Off Center effective in 1D, 2D and 3D (read, phase, slice)");
            param.setLocked(true);
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setUuid("31cbb500-f433-4ec4-be71-040c799506f4");
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setNumberEnum(NumberEnum.Location);
            param.setValue(asListNumber(0.0, 0.0, 0.0));
            param.setDefaultValue(asListNumber(0.0, 0.0, 0.0));
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_X("OFF_CENTER_FIELD_OF_VIEW_X") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_X");
            param.setDisplayedName("Location X");
            param.setDescription("Off-center distance in the R/L direction");
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setUuid("2a0bab88-5b63-4cdf-9052-2470f20f6400");
            param.setNumberEnum(NumberEnum.Location);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_Y("OFF_CENTER_FIELD_OF_VIEW_Y") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_Y");
            param.setDisplayedName("Location Y");
            param.setDescription("Off-center distance in the A/P direction");
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setUuid("7f4c9962-9cf1-46f8-9989-c3b26fcd68c8");
            param.setNumberEnum(NumberEnum.Location);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    OFF_CENTER_FIELD_OF_VIEW_Z("OFF_CENTER_FIELD_OF_VIEW_Z") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("OFF_CENTER_FIELD_OF_VIEW_Z");
            param.setDisplayedName("Location Z");
            param.setDescription("Off-center distance in the I/S direction");
            param.setGroup(EnumGroup.Gradient);
            param.setCategory(Category.Acquisition);
            param.setUuid("7a6763b6-fd44-4e39-852f-0c212092ab68");
            param.setNumberEnum(NumberEnum.Location);
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    ORIENTATION("ORIENTATION") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("ORIENTATION");
            param.setDisplayedName("Orientation");
            param.setDescription("Field Of View orientation");
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setUuid("6fcacd27-cc1b-4b27-bf57-cd0a1b0f0d9f");
            param.setValue("AXIAL");
            param.setDefaultValue("AXIAL");
            param.setSuggestedValues(asList("AXIAL", "CORONAL", "SAGITTAL", "OBLIQUE"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    PAROPT_PARAM("PAROPT_PARAM") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("PAROPT_PARAM");
            param.setDisplayedName("PAROPT_PARAM");
            param.setDescription("Name of the current optimised parameter");
            param.setLocked(true);
            param.setCategory(Category.Acquisition);
            param.setUuid("0ba24912-0f96-40ff-abcd-3b8a96127461");
            param.setValue("");
            param.setDefaultValue("PULSE_LENGTH");
            return param;
        }
    },

    PHASE_0("PHASE_0") {
        public Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("PHASE_0");
            param.setDisplayedName("PHASE_0");
            param.setDescription("PHASE_0.description");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Process);
            param.setUuid("5b5dd3a3-a147-4ea6-95fb-71eb39b4ed01");
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setNumberEnum(NumberEnum.Double);
            return param;
        }
    },

    PHASE_1("PHASE_1") {
        public Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("PHASE_1");
            param.setDisplayedName("PHASE_1");
            param.setDescription("PHASE_1.description");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Process);
            param.setUuid("13e11719-a5f6-4476-b480-3b38a263d390");
            param.setMinValue(-1.7976931348623157E308);
            param.setMaxValue(1.7976931348623157E308);
            param.setNumberEnum(NumberEnum.Double);
            return param;
        }
    },

    PHASE_APPLIED("PHASE_APPLIED") {
        public Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("PHASE_APPLIED");
            param.setDisplayedName("PHASE_APPLIED");
            param.setDescription("PHASE_APPLIED");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Process);
            param.setUuid("0e778a5f-7a08-479e-8c96-5f8b5e0df667");
            param.setValue(false);
            param.setDefaultValue(false);
            return param;
        }
    },

    PHASE_RESET("PHASE_RESET") {
        public Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("PHASE_RESET");
            param.setDisplayedName("PHASE_RESET");
            param.setDescription("Enable the Phase reset at the beginning of each scan");
            param.setLocked(true);
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("28263be9-67dc-4036-8aed-d86d81509d94");
            param.setValue(false);
            param.setDefaultValue(false);
            return param;
        }
    },

    PROBES("PROBES") {
        public Param build() {
            ListTextParam param = new ListTextParam();
            param.setName("PROBES");
            param.setDisplayedName("Probes");
            param.setDescription("Name of probes used for the transmit and reception");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Acquisition);
            param.setUuid("39fae8e8-bed5-412c-a2e9-c85de7170340");
            return param;
        }
    },

    RECEIVER_COUNT("RECEIVER_COUNT") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("RECEIVER_COUNT");
            param.setDisplayedName("No. Receivers");
            param.setDescription("Info: Number of reception channels");
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("2e92822f-2cc5-4e74-88c2-a4162ab7634a");
            param.setNumberEnum(NumberEnum.Integer);
            param.setMinValue(1);
            param.setMaxValue(32);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    },

    RECEIVER_GAIN("RECEIVER_GAIN") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("RECEIVER_GAIN");
            param.setDisplayedName("RG");
            param.setDescription("Receiver Gain");
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("8040fe64-485e-4faf-9c79-78267a41531e");
            param.setNumberEnum(NumberEnum.RxGain);
            param.setMinValue(0.0);
            param.setMaxValue(120.0);
            param.setValue(1.0);
            param.setDefaultValue(1.0);
            return param;
        }
    },

    REPETITION_TIME("REPETITION_TIME") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("REPETITION_TIME");
            param.setDisplayedName("TR");
            param.setDescription("Repetition Time");
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setUuid("895a8c34-03ef-400d-b6bc-cc43ee7d8620");
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(1.4101);
            param.setDefaultValue(0.2);
            return param;
        }
    },

    SEQUENCE_NAME("SEQUENCE_NAME") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("SEQUENCE_NAME");
            param.setDisplayedName("Sequence Name");
            param.setDescription("Info: Name of the sequence");
            param.setLocked(true);
            param.setCategory(Category.Acquisition);
            param.setUuid("28260898-d184-42f0-bf00-dd522235fd85");
            param.setValue("OnePulse_Slc");
            param.setDefaultValue("OnePulse_Slc");
            return param;
        }
    },

    SEQUENCE_TIME("SEQUENCE_TIME") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("SEQUENCE_TIME");
            param.setDisplayedName("Acquisition Time");
            param.setDescription("Info: Total acquisition time");
            param.setCategory(Category.Acquisition);
            param.setUuid("719b6f50-46b5-4a43-914d-16f9ab9631a6");
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setValue(15.511099999999999);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    SEQUENCE_VERSION("SEQUENCE_VERSION") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("SEQUENCE_VERSION");
            param.setDisplayedName("Sequence Version");
            param.setDescription("Info: Sequence version");
            param.setLocked(true);
            param.setGroup(EnumGroup.User);
            param.setCategory(Category.Acquisition);
            param.setUuid("fce8797d-e7c2-4278-9c47-28d0beef10ad");
            param.setValue("Version8.6");
            param.setDefaultValue("");
            return param;
        }
    },

    SETUP_MODE("SETUP_MODE") {
        public Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("SETUP_MODE");
            param.setDisplayedName("SETUP_MODE");
            param.setDescription("True during setup process / False for regular use for the sequence");
            param.setLocked(true);
            param.setCategory(Category.Acquisition);
            param.setUuid("1ceffca4-286b-449d-840b-5748e86d3953");
            param.setValue(false);
            param.setDefaultValue(false);
            return param;
        }
    },

    SLICE_THICKNESS("SLICE_THICKNESS") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("SLICE_THICKNESS");
            param.setDisplayedName("Slice Thickness");
            param.setDescription("Slice Thickness");
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setUuid("dd0ed93b-f314-4806-a057-4a054de13927");
            param.setNumberEnum(NumberEnum.Length);
            param.setMinValue(0.0);
            param.setMaxValue(1.7976931348623157E308);
            param.setValue(0.02);
            param.setDefaultValue(0.005);
            return param;
        }
    },

    SOFTWARE_VERSION("SOFTWARE_VERSION") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("SOFTWARE_VERSION");
            param.setDisplayedName("Software Version");
            param.setDescription("Info: Software version");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Miscellaneous);
            param.setUuid("f01d1fb9-2790-4aff-b378-ac3cf7c7a744");
            param.setValue("Software version");
            param.setDefaultValue("Software version");
            return param;
        }
    },

    SPECTRAL_WIDTH("SPECTRAL_WIDTH") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("SPECTRAL_WIDTH");
            param.setDisplayedName("BW");
            param.setDescription("Receiver bandwidth (SW, BW, bandwidth )");
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("8494b191-8921-4ba8-ba16-1325b68dbc9d");
            param.setNumberEnum(NumberEnum.SW);
            param.setMinValue(119.3);
            param.setMaxValue(3906000.0);
            param.setValue(20032.05128205128);
            param.setDefaultValue(12520.0);
            return param;
        }
    },

    SPECTRAL_WIDTH_OPT("SPECTRAL_WIDTH_OPT") {
        public Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("SPECTRAL_WIDTH_OPT");
            param.setDisplayedName("BW Option");
            param.setDescription("Use BW to calculate BW per pixel (Check) / Use  BW per pixel to calculate BW (Uncheck)");
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("59b4470f-0594-44ed-a428-4c0386060018");
            param.setValue(true);
            param.setDefaultValue(false);
            return param;
        }
    },

    SPECTRAL_WIDTH_PER_PIXEL("SPECTRAL_WIDTH_PER_PIXEL") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("SPECTRAL_WIDTH_PER_PIXEL");
            param.setDisplayedName("BW/pt");
            param.setDescription("Bandwidth per point ( FID res)");
            param.setGroup(EnumGroup.Reception);
            param.setCategory(Category.Acquisition);
            param.setUuid("f0ebce79-b6ce-41ce-a859-349689ef21d3");
            param.setNumberEnum(NumberEnum.SW);
            param.setMinValue(0.0);
            param.setMaxValue(1.0E8);
            param.setValue(2.4453187600160255);
            param.setDefaultValue(100.0);
            return param;
        }
    },

    STATION_NAME("STATION_NAME") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("STATION_NAME");
            param.setDisplayedName("Station Name");
            param.setDescription("Station name");
            param.setLocked(true);
            param.setGroup(EnumGroup.Miscellaneous);
            param.setCategory(Category.Miscellaneous);
            param.setUuid("6aa9ea34-3a23-4b92-8576-1e64fdef8f96");
            param.setValue("Station name");
            param.setDefaultValue("Station name");
            return param;
        }
    },

    SUBJECT_POSITION("SUBJECT_POSITION") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("SUBJECT_POSITION");
            param.setDisplayedName("Subject Position");
            param.setDescription("Subject position relative to the magnet");
            param.setGroup(EnumGroup.Dimension);
            param.setCategory(Category.Acquisition);
            param.setUuid("e9fa8c2b-1b43-4abc-8770-ca0a8c628e3b");
            param.setValue("HeadFirstProne");
            param.setDefaultValue("FeetFirstProne");
            param.setSuggestedValues(asList("HeadFirstProne", "HeadFirstSupine", "FeetFirstProne", "FeetFirstSupine"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    TRANSFORM_PLUGIN("TRANSFORM_PLUGIN") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("TRANSFORM_PLUGIN");
            param.setDisplayedName("Transform Plugin");
            param.setDescription("Transform the acquisition space to the k-space");
            param.setLocked(true);
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setUuid("7100fe7a-f4de-483f-9d0c-9f2cfa2ebef1");
            param.setValue("none");
            param.setDefaultValue("none");
            param.setSuggestedValues(asList("Accumulate1D2D", "Bordered2D", "Centered2D", "Centered2DRot", "EchoAntiechoTransform", "FSE_TRAIN_1D", "SEEPISequential", "Sequential", "Sequential2D", "Sequential2DInterleaved", "Sequential4D", "Sequential4D_Dummy2D", "Sequential4DBackAndForth", "Sequential4DCine", "SequentialMPRAGE"));
            return param;
        }
    },

    TRANSMIT_FREQ_1("TRANSMIT_FREQ_1") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TRANSMIT_FREQ_1");
            param.setDisplayedName("Transmit Freq 1");
            param.setDescription("Transmit frequency of the first sequence channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("16bc9bbe-de20-4b48-8666-a76f299664d8");
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(6.3806791188395776E7);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    TRANSMIT_FREQ_2("TRANSMIT_FREQ_2") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TRANSMIT_FREQ_2");
            param.setDisplayedName("Transmit Freq 2");
            param.setDescription("Transmit frequency of the second sequence channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("b225ebfe-dc4e-4e96-9e53-26161ee60a1d");
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(6.3806791188395776E7);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    TRANSMIT_FREQ_3("TRANSMIT_FREQ_3") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TRANSMIT_FREQ_3");
            param.setDisplayedName("Transmit Freq 3");
            param.setDescription("Transmit frequency of the third sequence channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("ffaadada-be0c-4b5b-8987-f440ae98bcf3");
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(6.3806791188395776E7);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    TRANSMIT_FREQ_4("TRANSMIT_FREQ_4") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TRANSMIT_FREQ_4");
            param.setDisplayedName("Transmit Freq 4");
            param.setDescription("Transmit frequency of the fourth sequence channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("e7241286-cf5f-4341-a459-01a5adb283dc");
            param.setNumberEnum(NumberEnum.Frequency);
            param.setMinValue(0.0);
            param.setMaxValue(3.0E9);
            param.setValue(6.3806791188395776E7);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    TRIGGER_CHANEL("TRIGGER_CHANEL") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("TRIGGER_CHANEL");
            param.setDisplayedName("Trigger Channel");
            param.setDescription("Choose the external trigger channels option (if available)");
            param.setLocked(true);
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setUuid("8a585759-6582-4d20-9d27-62fc264d589d");
            param.setValue("Ext1_XOR_Ext2");
            param.setDefaultValue("Ext1_XOR_Ext2");
            param.setSuggestedValues(asList("Ext1", "Ext2", "Ext1_AND_Ext2", "Ext1_XOR_Ext2"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    TRIGGER_EXTERNAL("TRIGGER_EXTERNAL") {
        public Param build() {
            BooleanParam param = new BooleanParam();
            param.setName("TRIGGER_EXTERNAL");
            param.setDisplayedName("Trigger External");
            param.setDescription("Enable to synchronize the acquisition with external trigger");
            param.setLocked(true);
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setUuid("e34cde71-5243-4a46-80cc-803730595787");
            param.setValue(false);
            param.setDefaultValue(false);
            return param;
        }
    },

    TRIGGER_TIME("TRIGGER_TIME") {
        public Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("TRIGGER_TIME");
            param.setDisplayedName("Trigger Delay");
            param.setDescription("Delays between trigger and acquisition start (Multiple values)");
            param.setLocked(true);
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setUuid("c48d0702-ef38-4a60-acef-66a9e1a9c25c");
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setNumberEnum(NumberEnum.Time);
            param.setValue(asListNumber(0.0, 0.1, 0.2, 0.3));
            param.setDefaultValue(asListNumber(0.01));
            return param;
        }
    },

    TX_AMP("TX_AMP") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_AMP");
            param.setDisplayedName("TX Amplitude");
            param.setDescription("Amplitude of the excitation RF pulse");
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("4e381691-8b2a-4b50-96c4-76206b85baae");
            param.setNumberEnum(NumberEnum.TxAmp);
            param.setMinValue(0.0);
            param.setMaxValue(100.0);
            param.setValue(71.94539940102987);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    TX_ATT("TX_ATT") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_ATT");
            param.setDisplayedName("TX Attenuation");
            param.setDescription("RF pulse attenuation");
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("a9f47542-bf8b-478e-af2d-58ad35a381b1");
            param.setNumberEnum(NumberEnum.TxAtt);
            param.setMinValue(0);
            param.setMaxValue(63);
            param.setValue(12);
            param.setDefaultValue(36);
            return param;
        }
    },

    TX_GAMMA_B1("TX_GAMMA_B1") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_GAMMA_B1");
            param.setDisplayedName("TX Gamma B1");
            param.setDescription("Info: Display the power in gamma B1 unit");
            param.setCategory(Category.Acquisition);
            param.setUuid("b97c56b6-2740-41a6-a9cf-f8bd27340c1f");
            param.setNumberEnum(NumberEnum.RfPower);
            param.setMinValue(0.0);
            param.setMaxValue(250000.0);
            param.setValue(5590.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    TX_LENGTH("TX_LENGTH") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_LENGTH");
            param.setDisplayedName("TX Length");
            param.setDescription("RF pulse length");
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setUuid("4c648c30-6369-4497-be76-d16537413f36");
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(0.0);
            param.setMaxValue(1.0);
            param.setValue(0.009998);
            param.setDefaultValue(0.3);
            return param;
        }
    },

    TX_NUTATION("TX_NUTATION") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("TX_NUTATION");
            param.setDisplayedName("Nutation");
            param.setDescription("Select the TX parameter to lineary increment to make a nutation curve. 'None' disables the option. ");
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("8122a193-fa92-419d-a767-b6b31cf52533");
            param.setValue("Voltage");
            param.setDefaultValue("None");
            param.setSuggestedValues(asList("None", "Amplitude", "Voltage", "Length"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    TX_NUTATION_AMP_MAX("TX_NUTATION_AMP_MAX") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_NUTATION_AMP_MAX");
            param.setDisplayedName("Nutation Amp Max");
            param.setDescription("Maximum value of TX amplitude ramp for nutation curve");
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("d7e3dd33-c7a4-4817-8c7a-477911b2c53c");
            param.setNumberEnum(NumberEnum.TxAmp);
            param.setMinValue(0.0);
            param.setMaxValue(100.0);
            param.setValue(71.94400969502779);
            param.setDefaultValue(100.0);
            return param;
        }
    },

    TX_NUTATION_AMP_MIN("TX_NUTATION_AMP_MIN") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_NUTATION_AMP_MIN");
            param.setDisplayedName("Nutation Amp Min");
            param.setDescription("Minimum value of TX amplitude ramp for nutation curve");
            param.setCategory(Category.Acquisition);
            param.setUuid("57764e4c-7656-4637-9f9e-cacc24b540fe");
            param.setNumberEnum(NumberEnum.TxAmp);
            param.setMinValue(0.0);
            param.setMaxValue(100.0);
            param.setValue(4.011840271370272);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    TX_NUTATION_AMP_VALUES("TX_NUTATION_AMP_VALUES") {
        public Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("TX_NUTATION_AMP_VALUES");
            param.setDisplayedName("Nutation Amp List");
            param.setDescription("Info: Values of the TX amplitude sampling points of the nutation curve");
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("fba9b2cf-1637-47f4-bff4-0847566f0c01");
            param.setMinValue(-2.147483648E9);
            param.setMaxValue(2.147483647E9);
            param.setNumberEnum(NumberEnum.PERCENT);
            param.setValue(asListNumber(4.011840271370272, 10.983267620216989, 17.96648303152289, 24.871099089953205, 31.72201298168632, 38.51258280896724, 45.27676456307265, 51.99434473215431, 58.65952634465031, 65.3377811289121));
            return param;
        }
    },

    TX_NUTATION_LENGTH_MAX("TX_NUTATION_LENGTH_MAX") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_NUTATION_LENGTH_MAX");
            param.setDisplayedName("Nutation Length Max");
            param.setDescription("Maximum value of TX length ramp for nutation curve");
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setUuid("3ebafd23-aa48-41af-a226-615f12a6232e");
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(9.999999999999999E-6);
            param.setMaxValue(1.0E9);
            param.setValue(0.009998);
            param.setDefaultValue(1.9999999999999998E-4);
            return param;
        }
    },

    TX_NUTATION_LENGTH_MIN("TX_NUTATION_LENGTH_MIN") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_NUTATION_LENGTH_MIN");
            param.setDisplayedName("Nutation Length Min");
            param.setDescription("Minimum value of TX length ramp for nutation curve");
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setUuid("4724f968-dc96-4580-bdb5-4515d127b8a8");
            param.setNumberEnum(NumberEnum.Time);
            param.setMinValue(4.9999999999999996E-6);
            param.setMaxValue(1.0);
            param.setValue(4.9999999999999996E-6);
            param.setDefaultValue(5.0E-4);
            return param;
        }
    },

    TX_NUTATION_LENGTH_VALUES("TX_NUTATION_LENGTH_VALUES") {
        public Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("TX_NUTATION_LENGTH_VALUES");
            param.setDisplayedName("Nutation Length List");
            param.setDescription("Info: Values of the TX length sampling points of the nutation curve");
            param.setGroup(EnumGroup.Delay);
            param.setCategory(Category.Acquisition);
            param.setUuid("68d34a6f-109e-4d28-b103-aecc1bebc2c7");
            param.setMinValue(0.0);
            param.setMaxValue(1.0E9);
            param.setNumberEnum(NumberEnum.Time);
            param.setValue(asListNumber(0.009998));
            return param;
        }
    },

    TX_NUTATION_VOLT_MAX("TX_NUTATION_VOLT_MAX") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_NUTATION_VOLT_MAX");
            param.setDisplayedName("Nutation Voltage Max");
            param.setDescription("Maximum value of TX voltage ramp for nutation curve");
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("03d2e0ea-98bb-4b39-8c74-b8d2ad9f6cb8");
            param.setNumberEnum(NumberEnum.Vpp);
            param.setMinValue(0.1);
            param.setMaxValue(1000.0);
            param.setValue(200.0);
            param.setDefaultValue(1.0);
            return param;
        }
    },

    TX_NUTATION_VOLT_MIN("TX_NUTATION_VOLT_MIN") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_NUTATION_VOLT_MIN");
            param.setDisplayedName("Nutation Voltage Min");
            param.setDescription("Minimum value of TX voltage ramp for nutation curve");
            param.setCategory(Category.Acquisition);
            param.setUuid("a6b69fb0-47ef-47c9-a4f7-b01af2056a77");
            param.setNumberEnum(NumberEnum.Vpp);
            param.setMinValue(0.0);
            param.setMaxValue(1000.0);
            param.setValue(10.696);
            param.setDefaultValue(0.1);
            return param;
        }
    },

    TX_NUTATION_VOLT_VALUES("TX_NUTATION_VOLT_VALUES") {
        public Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("TX_NUTATION_VOLT_VALUES");
            param.setDisplayedName("Nutation Voltage List");
            param.setDescription("Info: Values of the TX voltage sampling points of the nutation curve");
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("809919bb-ef5a-4eca-a3ea-d16e47575d1f");
            param.setMinValue(0.0);
            param.setMaxValue(1000.0);
            param.setNumberEnum(NumberEnum.Vpp);
            param.setValue(asListNumber(10.696, 29.625999999999998, 48.556, 67.486, 86.416, 105.346, 124.276, 143.206, 162.136, 181.066));
            return param;
        }
    },

    TX_NUT_STEP_NUMBER("TX_NUT_STEP_NUMBER") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_NUT_STEP_NUMBER");
            param.setDisplayedName("Nutation Step Number");
            param.setDescription("Number of incremental steps used to sample the nutation curve");
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("2e0503a2-2b83-4378-80b6-098293c7a468");
            param.setNumberEnum(NumberEnum.Integer);
            param.setMinValue(0);
            param.setMaxValue(256);
            param.setValue(11);
            param.setDefaultValue(1);
            return param;
        }
    },

    TX_POWER("TX_POWER") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_POWER");
            param.setDisplayedName("TX Power");
            param.setDescription("Info: Display the pulse power amplitude that corresponds to Tx Amplitude and Attenuation");
            param.setCategory(Category.Acquisition);
            param.setUuid("e35ca420-08f7-426c-a521-7bd92b7994b1");
            param.setNumberEnum(NumberEnum.Power);
            param.setMinValue(0.0);
            param.setMaxValue(10000.0);
            param.setValue(100.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    TX_POWER_INPUT("TX_POWER_INPUT") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("TX_POWER_INPUT");
            param.setDisplayedName("TX Power Input");
            param.setDescription("Select the input parameter to calculate the pulse power amplitude. Warning: calibrate the 90°-pulse to use the Flip Angle");
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("70f9d15f-1a81-4940-9ac6-9b688fbe40cb");
            param.setValue("Voltage");
            param.setDefaultValue("Att/Amp");
            param.setSuggestedValues(asList("Att/Amp", "Voltage", "Flip Angle"));
            param.setRestrictedToSuggested(true);
            return param;
        }
    },

    TX_ROUTE("TX_ROUTE") {
        public Param build() {
            ListNumberParam param = new ListNumberParam();
            param.setName("TX_ROUTE");
            param.setDisplayedName("TX Route");
            param.setDescription("Info: Physical transmit channel");
            param.setLocked(true);
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("a8bd7d5e-4107-4527-9041-6d7d4bb0edf4");
            param.setMinValue(-2147483648);
            param.setMaxValue(2147483647);
            param.setNumberEnum(NumberEnum.Integer);
            param.setValue(asListNumber(0));
            return param;
        }
    },

    TX_SHAPE("TX_SHAPE") {
        public Param build() {
            TextParam param = new TextParam();
            param.setName("TX_SHAPE");
            param.setDisplayedName("TX Shape");
            param.setDescription("RF Pulse shape");
            param.setCategory(Category.Acquisition);
            param.setUuid("f660e6d3-74a2-4111-bb56-dbd69fe713df");
            param.setValue("GAUSSIAN");
            param.setDefaultValue("GAUSSIAN");
            param.setSuggestedValues(asList("GAUSSIAN", "SINC3", "HARD", "SINC5"));
            return param;
        }
    },

    TX_VOLTAGE("TX_VOLTAGE") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("TX_VOLTAGE");
            param.setDisplayedName("TX Voltage");
            param.setDescription("RF voltage amplitude peak-to-peak");
            param.setGroup(EnumGroup.Emission);
            param.setCategory(Category.Acquisition);
            param.setUuid("a853cdcd-a18e-4b8d-b8c8-fb29d9d51267");
            param.setNumberEnum(NumberEnum.Vpp);
            param.setMinValue(0.0);
            param.setMaxValue(1000.0);
            param.setValue(200.0);
            param.setDefaultValue(0.0);
            return param;
        }
    },

    USER_MATRIX_DIMENSION_1D("USER_MATRIX_DIMENSION_1D") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("USER_MATRIX_DIMENSION_1D");
            param.setDisplayedName("Matrix 1D - FID");
            param.setDescription("Number of points of the FID  (user matrix dimension 1D)");
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setUuid("7d4b8a01-7a8c-41d0-b6e3-69cbf41c657e");
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(8192);
            param.setDefaultValue(128);
            return param;
        }
    },

    USER_MATRIX_DIMENSION_2D("USER_MATRIX_DIMENSION_2D") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("USER_MATRIX_DIMENSION_2D");
            param.setDisplayedName("Matrix 2D");
            param.setDescription("Number of repetitions  (user matrix dimension 2D)");
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setUuid("be07fdb8-b371-4d24-85aa-cd55c25d3fea");
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(11);
            param.setDefaultValue(128);
            return param;
        }
    },

    USER_MATRIX_DIMENSION_3D("USER_MATRIX_DIMENSION_3D") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("USER_MATRIX_DIMENSION_3D");
            param.setDisplayedName("Matrix 3D");
            param.setDescription("Number of repetitions  (user matrix dimension 3D)");
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setUuid("fc754193-e5cc-4b7d-9b47-f5f391a58629");
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    },

    USER_MATRIX_DIMENSION_4D("USER_MATRIX_DIMENSION_4D") {
        public Param build() {
            NumberParam param = new NumberParam();
            param.setName("USER_MATRIX_DIMENSION_4D");
            param.setDisplayedName("No. trigger delay (4D)");
            param.setDescription("Info: Number of trigger delay (user maxtrix 4D)");
            param.setLocked(true);
            param.setGroup(EnumGroup.Scan);
            param.setCategory(Category.Acquisition);
            param.setUuid("fa86c30b-8d69-40f6-b649-e7527a1cb83e");
            param.setNumberEnum(NumberEnum.Scan);
            param.setMinValue(0);
            param.setMaxValue(65536);
            param.setValue(1);
            param.setDefaultValue(1);
            return param;
        }
    };

    //--

    private final String name;

    private U(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    //--

    private static List<Number> asListNumber(Number ... numbers) {
        return asList(numbers);
    }
}
