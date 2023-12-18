//-- generated code, will be overwritten at each recompilation

package rs2d.sequence.onpulsegradslc;

import rs2d.spinlab.sequenceGenerator.GeneratorSequenceParamEnum;

public enum S implements GeneratorSequenceParamEnum {
    Enable_shape_grad_1("Enable_shape_grad_1"),
    Enable_shape_grad_2("Enable_shape_grad_2"),
    Enable_shape_grad_3("Enable_shape_grad_3"),
    enabled_slice("enabled_slice"),
    enabled_spoiler("enabled_spoiler"),
    Ext_trig_source("Ext_trig_source"),
    Grad_enable_phase1d("Grad_enable_phase1d"),
    Grad_enable_phase2d("Grad_enable_phase2d"),
    Grad_enable_spoiler_phase1d("Grad_enable_spoiler_phase1d"),
    Grad_enable_spoiler_phase2d("Grad_enable_spoiler_phase2d"),
    Gradient_angle_phi("Gradient_angle_phi"),
    Gradient_angle_psi("Gradient_angle_psi"),
    Gradient_angle_theta("Gradient_angle_theta"),
    Intermediate_frequency("Intermediate_frequency"),
    LO_att("LO_att"),
    Nb_1d("Nb_1d"),
    Nb_2d("Nb_2d"),
    Nb_3d("Nb_3d"),
    Nb_4d("Nb_4d"),
    Nb_point("Nb_point"),
    Phase_reset("Phase_reset"),
    Pre_scan("Pre_scan"),
    Spectral_width("Spectral_width"),
    Synchro_Trigger("Synchro_Trigger"),
    Tx_att("Tx_att"),
    Tx_blanking("Tx_blanking"),
    Tx_frequency("Tx_frequency"),
    Tx_nucleus("Tx_nucleus"),
    Freq_offset("Freq_offset"),
    Frequency_offset_init("Frequency_offset_init"),
    Grad_amp_phase_1D_prep("Grad_amp_phase_1D_prep"),
    Grad_amp_phase_2D_prep("Grad_amp_phase_2D_prep"),
    Grad_amp_slice("Grad_amp_slice"),
    Grad_amp_slice_ref("Grad_amp_slice_ref"),
    Grad_amp_spoiler("Grad_amp_spoiler"),
    Grad_amp_spoiler_phase1d("Grad_amp_spoiler_phase1d"),
    Grad_amp_spoiler_phase2d("Grad_amp_spoiler_phase2d"),
    Grad_shape_1("Grad_shape_1"),
    Grad_shape_2("Grad_shape_2"),
    Grad_shape_3("Grad_shape_3"),
    Grad_shape_amp_1("Grad_shape_amp_1"),
    Grad_shape_amp_2("Grad_shape_amp_2"),
    Grad_shape_amp_3("Grad_shape_amp_3"),
    Grad_shape_down("Grad_shape_down"),
    Grad_shape_up("Grad_shape_up"),
    Rx_gain("Rx_gain"),
    Time_FIR_delay("Time_FIR_delay"),
    Time_grad_ramp("Time_grad_ramp"),
    Time_grad_ramp_blanking("Time_grad_ramp_blanking"),
    Time_grad_ref("Time_grad_ref"),
    Time_grad_spoil("Time_grad_spoil"),
    Time_grad_spoil_ramp("Time_grad_spoil_ramp"),
    Time_last_delay("Time_last_delay"),
    Time_rx("Time_rx"),
    Time_rx_continue("Time_rx_continue"),
    Time_shapegrad_1("Time_shapegrad_1"),
    Time_shapegrad_2("Time_shapegrad_2"),
    Time_shapegrad_3("Time_shapegrad_3"),
    Time_te_delay("Time_te_delay"),
    Time_trigger_delay("Time_trigger_delay"),
    Time_tx("Time_tx"),
    Tx_amp("Tx_amp"),
    Tx_freq_offset("Tx_freq_offset"),
    Tx_phase("Tx_phase"),
    Tx_shape("Tx_shape"),
    Tx_shape_phase("Tx_shape_phase");

    //--

    private final String name;

    private S(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
