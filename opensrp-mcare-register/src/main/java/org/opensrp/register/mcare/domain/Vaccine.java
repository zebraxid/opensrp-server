package org.opensrp.register.mcare.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vaccine {
    public static String Key = "vaccine";
    private VaccineName name;
    private VaccineDose dose;

    public VaccineName getName() {
        return name;
    }

    public VaccineDose getDose() {
        return dose;
    }

    public Vaccine(VaccineName name, VaccineDose dose) {
        this.name = name;
        this.dose = dose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vaccine vaccine = (Vaccine) o;

        if (getName() != vaccine.getName()) return false;
        return getDose() == vaccine.getDose();
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (getDose() != null ? getDose().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        return name.getName() + (dose != null ? dose.getValueInString() : "");
    }

    /**
     * String format for vaccines bcg, penta1, tt1, tt2.
     * @param vaccinesStr
     * @return
     */
    public static List<Vaccine> extractVaccinesFromStr(String vaccinesStr) {
        List<Vaccine> vaccineList = new ArrayList<>();
        String[] vaccines = vaccinesStr.split(" ");
        for (String vaccine : vaccines) {
            boolean containDoseNumber = vaccine.contains("1") || vaccine.contains("2") || vaccine.contains("3") || vaccine.contains("4") || vaccine.contains("5");
            if(containDoseNumber) {
                String dose = vaccine.substring(vaccine.length()-1);
                String name = vaccine.substring(0, vaccine.length()-1);
                vaccineList.add(new Vaccine(VaccineName.fromStr(name), VaccineDose.fromStr(dose)));
            }else {
                vaccineList.add(new Vaccine(VaccineName.fromStr(vaccine), null));
            }
        }

        return vaccineList;
    }

    public enum VaccineName {
        BCG("bcg"),
        PENTA("penta"),
        TT("tt");
        private String value;
        private static final Map<String , VaccineName> map = new HashMap();

        static {
            for (VaccineName vaccineName : VaccineName.values()) {
                map.put(vaccineName.value, vaccineName);
            }
        }

        VaccineName(String value) {
            this.value = value;
        }

        public String getName() {
            return value;
        }

        public static VaccineName fromStr(String value) {
            if(map.containsKey(value.toLowerCase().trim())){
                return map.get(value.toLowerCase().trim());
            }else {
                throw new IllegalArgumentException();
            }
        }
    }

    public enum VaccineDose {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5);

        public int value;

        VaccineDose(int value) {
            this.value = value;
        }

        private static Map<Integer, VaccineDose> map = new HashMap<Integer, VaccineDose>();

        static {
            for (VaccineDose vaccineDose : VaccineDose.values()) {
                map.put(vaccineDose.value, vaccineDose);
            }
        }

        public static VaccineDose fromStr(String value) {
            if (value == null || value.isEmpty()) {
                throw new IllegalArgumentException();
            } else {
                return fromInt(Integer.parseInt(value));
            }
        }

        public static List<VaccineDose> extractVaccineDoseListFrom(String ttDoseStr) {
            String[] ttDoseStrList = ttDoseStr.split(" ");
            List<VaccineDose> vaccineDoses = new ArrayList<>();
            for (int i = 0; i < ttDoseStrList.length; i++) {
                vaccineDoses.add(VaccineDose.fromStr(ttDoseStrList[i]));
            }
            return vaccineDoses;
        }

        public static VaccineDose fromInt(int value) {
            if (map.containsKey(value)) {
                return map.get(value);
            }

            throw new IllegalArgumentException();
        }

        public Integer getValue() {
            return this.value;
        }

        public String getValueInString() {
            return this.getValue().toString();
        }
    }

}
