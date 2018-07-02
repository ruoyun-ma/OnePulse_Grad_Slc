//-- generated code, will be overwritten at each recompilation

package rs2d.sequence.onpulseslc;


public interface OnepulseSlcSequenceParams {

    //-- public params

    /**
     * Group: Gradient, Type: BooleanParam
     */
    String enabled_slice = "enabled_slice";

    /**
     * Group: Gradient, Type: BooleanParam
     */
    String enabled_spoiler = "enabled_spoiler";

    /**
     * Group: Delay, Type: TextParam
     */
    String Ext_trig_source = "Ext_trig_source";

    /**
     * Group: Miscellaneous, Type: NumberParam - Angle (°)
     */
    String Gradient_angle_phi = "Gradient_angle_phi";

    /**
     * Group: Miscellaneous, Type: NumberParam - Angle (°)
     */
    String Gradient_angle_psi = "Gradient_angle_psi";

    /**
     * Group: Miscellaneous, Type: NumberParam - Angle (°)
     */
    String Gradient_angle_theta = "Gradient_angle_theta";

    /**
     * Group: Reception, Type: NumberParam - Frequency (Hz)
     */
    String Intermediate_frequency = "Intermediate_frequency";

    /**
     * Group: Dimension, Type: NumberParam - Scan
     */
    String Nb_1d = "Nb_1d";

    /**
     * Group: Dimension, Type: NumberParam - Scan
     */
    String Nb_2d = "Nb_2d";

    /**
     * Group: Dimension, Type: NumberParam - Scan
     */
    String Nb_3d = "Nb_3d";

    /**
     * Group: Dimension, Type: NumberParam - Scan
     */
    String Nb_4d = "Nb_4d";

    /**
     * Group: Reception, Type: NumberParam - AcquPoint (pts)
     */
    String Nb_point = "Nb_point";

    /**
     * Group: Transmit, Type: BooleanParam
     */
    String Phase_reset = "Phase_reset";

    /**
     * Group: Dimension, Type: NumberParam - Scan
     */
    String Pre_scan = "Pre_scan";

    /**
     * Group: Reception, Type: NumberParam - RxGain (dB)
     */
    String Rx_gain = "Rx_gain";

    /**
     * Group: Reception, Type: NumberParam - SW (Hz)
     */
    String Spectral_width = "Spectral_width";

    /**
     * Group: Delay, Type: EnumParam
     */
    String Synchro_trigger = "Synchro_trigger";

    /**
     * Group: Transmit, Type: NumberParam - TxAtt (dB)
     */
    String Tx_att = "Tx_att";

    /**
     * Group: Transmit, Type: NumberParam - Frequency (Hz)
     */
    String Tx_frequency = "Tx_frequency";

    /**
     * Group: Miscellaneous, Type: TextParam
     */
    String Tx_nucleus = "Tx_nucleus";


    //-- public tables

    /**
     * Group: Transmit, Order: 2D - FreqOffset (Hz)
     */
    String Freq_offset = "Freq_offset";

    /**
     * Group: Transmit, Order: 1D - FreqOffset (Hz)
     */
    String Frequency_offset_init = "Frequency_offset_init";

    /**
     * Group: Gradient, Order: 4D+Loop - GradAmp (%)
     */
    String Grad_amp_slice = "Grad_amp_slice";

    /**
     * Group: Gradient, Order: 4D+Loop - GradAmp (%)
     */
    String Grad_amp_slice_ref = "Grad_amp_slice_ref";

    /**
     * Group: Gradient, Order: 4D+Loop - GradAmp (%)
     */
    String Grad_amp_spoiler = "Grad_amp_spoiler";

    /**
     * Group: Gradient, Order: 1D - GradShape (%)
     */
    String Grad_shape_down = "Grad_shape_down";

    /**
     * Group: Gradient, Order: 1D - GradShape (%)
     */
    String Grad_shape_up = "Grad_shape_up";

    /**
     * Group: Delay, Order: 1D - Time (s)
     */
    String Time_FIR_delay = "Time_FIR_delay";

    /**
     * Group: Delay, Order: 4D+Loop - Time (s)
     */
    String Time_grad_ramp = "Time_grad_ramp";

    /**
     * Group: Delay, Order: 4D+Loop - Time (s)
     */
    String Time_grad_ramp_blanking = "Time_grad_ramp_blanking";

    /**
     * Group: Delay, Order: 1D - Time (s)
     */
    String Time_grad_ref = "Time_grad_ref";

    /**
     * Group: Delay, Order: 1D - Time (s)
     */
    String Time_grad_spoil = "Time_grad_spoil";

    /**
     * Group: Delay, Order: 1D - Time (s)
     */
    String Time_grad_spoil_ramp = "Time_grad_spoil_ramp";

    /**
     * Group: Delay, Order: 2D - Time (s)
     */
    String Time_last_delay = "Time_last_delay";

    /**
     * Group: Reception, Order: 1D - Time (s)
     */
    String Time_rx = "Time_rx";

    /**
     * Group: Delay, Order: 2D - Time (s)
     */
    String Time_te_delay = "Time_te_delay";

    /**
     * Group: Delay, Order: 4D - Time (s)
     */
    String Time_trigger_delay = "Time_trigger_delay";

    /**
     * Group: Transmit, Order: 2D - Time (s)
     */
    String Time_tx = "Time_tx";

    /**
     * Group: Transmit, Order: 4D+Loop - TxAmp (%)
     */
    String Tx_amp1 = "Tx_amp1";

    /**
     * Group: Transmit, Order: 2D - FreqOffset (Hz)
     */
    String Tx_freq_offset = "Tx_freq_offset";

    /**
     * Group: Transmit, Order: 1D - Phase (°)
     */
    String Tx_phase = "Tx_phase";

    /**
     * Group: Transmit, Order: 1D - TxShape (%)
     */
    String Tx_shape = "Tx_shape";

    /**
     * Group: Transmit, Order: 1D - Phase (°)
     */
    String Tx_shape_phase = "Tx_shape_phase";

}