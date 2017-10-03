package org.opensrp.register.mcare.report.mis1.birthAndDeath;

import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asha on 9/25/17.
 */
public class AliveDeathCount {
    private Map<String , String> dooData;

    public Map<String , String> getDooData(){

        return dooData;
    }

    public static class AliveDeathCountBuilder{
        private Map<String , String> dooData;

        public AliveDeathCountBuilder(){

            dooData = new HashMap<>();
        }

        public AliveDeathCount.AliveDeathCountBuilder numLiveBirth(long number){
            dooData.put("Num_Live_Birth" , "2");
            return this ;
        }

        public AliveDeathCount.AliveDeathCountBuilder DooDate(String dooDateStr){
            dooData.put("DOO" ,dooDateStr );
            return this;
        }

        public AliveDeathCount.AliveDeathCountBuilder childWithUnderWeight(long weight){
            dooData.put("Child_Weight","2");
            return this;
        }

        public AliveDeathCount build() {
            AliveDeathCount aliveDeathCount = new AliveDeathCount(this);
            this.dooData.clear();
            return aliveDeathCount;
        }

    }

    private AliveDeathCount(AliveDeathCount.AliveDeathCountBuilder aliveDeathCountBuilder) {
        this.dooData = new HashMap<>(aliveDeathCountBuilder.dooData);
    }
}
