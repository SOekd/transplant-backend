package com.transplantados;

import com.transplantados.patient.BloodType;
import com.transplantados.patient.Patient;
import com.transplantados.patient.PatientRepository;
import com.transplantados.patient.Sex;
import com.transplantados.professional.Professional;
import com.transplantados.professional.ProfessionalRepository;
import com.transplantados.transplant.Transplant;
import com.transplantados.transplant.TransplantRepository;
import com.transplantados.variables.DeviceVariable;
import com.transplantados.variables.Variable;
import com.transplantados.variables.VariableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationDataLoader implements ApplicationRunner {

    private final VariableRepository variableRepository;
    private final TransplantRepository transplantRepository;
    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        if (professionalRepository.count() > 0) {
            return;
        }

        Patient patient = Patient.builder()
                .name("Maria Oliveira")
                .cpf("123.456.789-00")
                .email("patient@gmail.com")
                .password(passwordEncoder().encode("senha123"))
                .cellphone("47999998888")
                .alternativeCellphone("47988887777")
                .birthDate(LocalDate.of(1985, 5, 15))
                .bloodType(BloodType.A_NEGATIVE)
                .sex(Sex.FEMALE)
                .build();
        patientRepository.save(patient);

        Professional professional = Professional.builder()
                .name("Dr. João Silva")
                .email("professional@gmail.com")
                .password(passwordEncoder().encode("senha123"))
                .build();
        professionalRepository.save(professional);

        Variable weight = variableRepository.save(Variable.builder().name("Peso Corporal").unityOfMeasure("kg").showInLogBook(true).deviceVariable(DeviceVariable.WEIGHT).build());
        Variable height = variableRepository.save(Variable.builder().name("Altura").unityOfMeasure("cm").showInLogBook(true).deviceVariable(DeviceVariable.HEIGHT).build());
        Variable heartRate = variableRepository.save(Variable.builder().name("Frequência Cardíaca").unityOfMeasure("bpm").showInLogBook(true).deviceVariable(DeviceVariable.HEART_RATE).build());
        Variable systolicBP = variableRepository.save(Variable.builder().name("Pressão Arterial Sistólica").unityOfMeasure("mmHg").showInLogBook(true).deviceVariable(DeviceVariable.BLOOD_PRESSURE_SYSTOLIC).build());
        Variable diastolicBP = variableRepository.save(Variable.builder().name("Pressão Arterial Diastólica").unityOfMeasure("mmHg").showInLogBook(true).deviceVariable(DeviceVariable.BLOOD_PRESSURE_DIASTOLIC).build());
        Variable bloodGlucose = variableRepository.save(Variable.builder().name("Glicemia Capilar").unityOfMeasure("mg/dL").showInLogBook(true).deviceVariable(DeviceVariable.BLOOD_GLUCOSE).build());

        Variable temperature = variableRepository.save(Variable.builder().name("Temperatura Corporal").unityOfMeasure("°C").showInLogBook(true).build());
        Variable oxygenSaturation = variableRepository.save(Variable.builder().name("Saturação de Oxigênio").unityOfMeasure("%").showInLogBook(true).build());
        Variable urineOutput = variableRepository.save(Variable.builder().name("Volume Urinário (Diurese)").unityOfMeasure("mL").showInLogBook(true).build());

        Variable fever = variableRepository.save(Variable.builder().name("Febre (>37.8°C)").unityOfMeasure("N/A").isSwitch(true).showInLogBook(true).build());
        Variable chills = variableRepository.save(Variable.builder().name("Calafrios").unityOfMeasure("N/A").isSwitch(true).showInLogBook(true).build());
        Variable persistentCough = variableRepository.save(Variable.builder().name("Tosse Persistente").unityOfMeasure("N/A").isSwitch(true).showInLogBook(true).build());
        Variable shortnessOfBreath = variableRepository.save(Variable.builder().name("Falta de Ar").unityOfMeasure("N/A").isSwitch(true).showInLogBook(true).build());
        Variable edema = variableRepository.save(Variable.builder().name("Presença de Edema/Inchaço").unityOfMeasure("N/A").isSwitch(true).showInLogBook(true).build());

        Variable tacrolimus = variableRepository.save(Variable.builder().name("Nível Sérico de Tacrolimo").unityOfMeasure("ng/mL").build());
        Variable cyclosporine = variableRepository.save(Variable.builder().name("Nível Sérico de Ciclosporina").unityOfMeasure("ng/mL").build());
        Variable hemoglobin = variableRepository.save(Variable.builder().name("Hemoglobina").unityOfMeasure("g/dL").build());
        Variable leukocytes = variableRepository.save(Variable.builder().name("Leucócitos").unityOfMeasure("x10³/µL").build());
        Variable platelets = variableRepository.save(Variable.builder().name("Plaquetas").unityOfMeasure("x10³/µL").build());
        Variable sodium = variableRepository.save(Variable.builder().name("Sódio (Na)").unityOfMeasure("mEq/L").build());
        Variable potassium = variableRepository.save(Variable.builder().name("Potássio (K)").unityOfMeasure("mEq/L").build());
        Variable cmvViralLoad = variableRepository.save(Variable.builder().name("Carga Viral CMV").unityOfMeasure("cópias/mL").build());

        List<Variable> generalVariables = Arrays.asList(
                weight, height, heartRate, systolicBP, diastolicBP, bloodGlucose, temperature, oxygenSaturation,
                urineOutput, fever, chills, persistentCough, shortnessOfBreath, edema, tacrolimus, cyclosporine,
                hemoglobin, leukocytes, platelets, sodium, potassium, cmvViralLoad
        );

        Variable creatinine = variableRepository.save(Variable.builder().name("Creatinina Sérica").unityOfMeasure("mg/dL").build());
        Variable urea = variableRepository.save(Variable.builder().name("Ureia Sérica").unityOfMeasure("mg/dL").build());
        Variable egfr = variableRepository.save(Variable.builder().name("Taxa de Filtração Glomerular (TFGe)").unityOfMeasure("mL/min/1.73m²").build());
        Variable proteinuria = variableRepository.save(Variable.builder().name("Proteinúria de 24h").unityOfMeasure("mg/24h").build());
        Variable bkViralLoad = variableRepository.save(Variable.builder().name("Carga Viral Poliomavírus (BK)").unityOfMeasure("cópias/mL").build());

        Variable ast = variableRepository.save(Variable.builder().name("AST / TGO").unityOfMeasure("U/L").build());
        Variable alt = variableRepository.save(Variable.builder().name("ALT / TGP").unityOfMeasure("U/L").build());
        Variable bilirubin = variableRepository.save(Variable.builder().name("Bilirrubina Total").unityOfMeasure("mg/dL").build());
        Variable alkalinePhosphatase = variableRepository.save(Variable.builder().name("Fosfatase Alcalina").unityOfMeasure("U/L").build());
        Variable albumin = variableRepository.save(Variable.builder().name("Albumina").unityOfMeasure("g/dL").build());
        Variable inr = variableRepository.save(Variable.builder().name("INR (RNI)").unityOfMeasure("ratio").build());
        Variable ascites = variableRepository.save(Variable.builder().name("Presença de Ascite").unityOfMeasure("N/A").isSwitch(true).showInLogBook(true).build());

        Variable ejectionFraction = variableRepository.save(Variable.builder().name("Fração de Ejeção (Eco)").unityOfMeasure("%").build());
        Variable bnp = variableRepository.save(Variable.builder().name("Peptídeo Natriurético (BNP)").unityOfMeasure("pg/mL").build());

        Variable fev1 = variableRepository.save(Variable.builder().name("VEF1 (Espirometria)").unityOfMeasure("L").showInLogBook(true).build());
        Variable fvc = variableRepository.save(Variable.builder().name("CVF (Espirometria)").unityOfMeasure("L").showInLogBook(true).build());

        Variable amylase = variableRepository.save(Variable.builder().name("Amilase").unityOfMeasure("U/L").build());
        Variable lipase = variableRepository.save(Variable.builder().name("Lipase").unityOfMeasure("U/L").build());
        Variable hba1c = variableRepository.save(Variable.builder().name("Hemoglobina Glicada (HbA1c)").unityOfMeasure("%").build());

        Variable chimerism = variableRepository.save(Variable.builder().name("Quimerismo").unityOfMeasure("% doador").build());
        Variable neutrophils = variableRepository.save(Variable.builder().name("Contagem de Neutrófilos").unityOfMeasure("cél/µL").build());
        Variable gvhdSkin = variableRepository.save(Variable.builder().name("DECH Aguda - Pele (Rash)").unityOfMeasure("N/A").isSwitch(true).showInLogBook(true).build());
        Variable gvhdGastro = variableRepository.save(Variable.builder().name("DECH Aguda - Gastro (Diarreia)").unityOfMeasure("N/A").isSwitch(true).showInLogBook(true).build());

        List<Variable> kidneyVariables = new ArrayList<>(generalVariables);
        kidneyVariables.addAll(Arrays.asList(creatinine, urea, egfr, proteinuria, bkViralLoad));
        transplantRepository.save(Transplant.builder().name("Transplante Renal").variables(kidneyVariables).build());

        List<Variable> liverVariables = new ArrayList<>(generalVariables);
        liverVariables.addAll(Arrays.asList(ast, alt, bilirubin, alkalinePhosphatase, albumin, inr, ascites));
        transplantRepository.save(Transplant.builder().name("Transplante Hepático").variables(liverVariables).build());

        List<Variable> heartVariables = new ArrayList<>(generalVariables);
        heartVariables.addAll(Arrays.asList(ejectionFraction, bnp));
        transplantRepository.save(Transplant.builder().name("Transplante Cardíaco").variables(heartVariables).build());

        List<Variable> lungVariables = new ArrayList<>(generalVariables);
        lungVariables.addAll(Arrays.asList(fev1, fvc));
        transplantRepository.save(Transplant.builder().name("Transplante Pulmonar").variables(lungVariables).build());

        List<Variable> pancreasVariables = new ArrayList<>(generalVariables);
        pancreasVariables.addAll(Arrays.asList(amylase, lipase, hba1c));
        transplantRepository.save(Transplant.builder().name("Transplante de Pâncreas").variables(pancreasVariables).build());

        List<Variable> boneMarrowVariables = new ArrayList<>(generalVariables);
        boneMarrowVariables.addAll(Arrays.asList(chimerism, neutrophils, gvhdSkin, gvhdGastro));
        transplantRepository.save(Transplant.builder().name("Transplante de Medula Óssea").variables(boneMarrowVariables).build());
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}