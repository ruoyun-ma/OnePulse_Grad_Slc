//-- generated code, will be overwritten at each recompilation

package rs2d.sequence.onpulseslc;

import rs2d.spinlab.tools.param.*;
import rs2d.spinlab.tools.table.*;
import rs2d.spinlab.tools.role.RoleEnum;
import rs2d.spinlab.sequenceGenerator.GeneratorSequenceParamEnum;

import java.util.List;
import static java.util.Arrays.asList;

public enum S implements GeneratorSequenceParamEnum {
    enabled_slice("enabled_slice"),
    enabled_spoiler("enabled_spoiler"),
    Ext_trig_source("Ext_trig_source"),
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
    Synchro_trigger("Synchro_trigger"),
    Tx_att("Tx_att"),
    Tx_blanking("Tx_blanking"),
    Tx_frequency("Tx_frequency"),
    Tx_nucleus("Tx_nucleus"),
    Freq_offset("Freq_offset"),
    Frequency_offset_init("Frequency_offset_init"),
    Grad_amp_slice("Grad_amp_slice"),
    Grad_amp_slice_ref("Grad_amp_slice_ref"),
    Grad_amp_spoiler("Grad_amp_spoiler"),
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
    Time_te_delay("Time_te_delay"),
    Time_trigger_delay("Time_trigger_delay"),
    Time_tx("Time_tx"),
    Tx_amp1("Tx_amp1"),
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