package com.infosys.test.automation.dto;

import com.infosys.test.automation.constants.CondOperatorConstants;
import org.json.simple.parser.ParseException;

import java.util.Locale;

public class MultiCondConfig implements CondConfig {
    private String logicalOp;
    private SingleCondConfig filterElem1;
    private SingleCondConfig filterElem2;
    private MultiCondConfig filterElem3;
    private MultiCondConfig filterElem4;
    private MultiCondConfig(){

    }
    private MultiCondConfig(String logicalOp, SingleCondConfig filterElem1,
                            SingleCondConfig filterElem2, MultiCondConfig filterElem3,
                            MultiCondConfig filterElem4){
        this.logicalOp = logicalOp;
        this.filterElem1 = filterElem1;
        this.filterElem2 = filterElem2;
        this.filterElem3 = filterElem3;
        this.filterElem4 = filterElem4;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" { element type -> multi condition element\n");
        stringBuilder.append(" , logical operator -> "+logicalOp+"\n");
        if (filterElem1 != null){
            stringBuilder.append(" , single condition element -> "+filterElem1.toString()+"\n");
        }
        if (filterElem2 != null){
            stringBuilder.append(" , single condition element -> "+filterElem2.toString()+"\n");
        }
        if (filterElem3 != null){
            stringBuilder.append(" , multi condition element -> "+filterElem3.toString()+"\n");
        }
        if (filterElem4 != null){
            stringBuilder.append(" , multi condition element -> "+filterElem4.toString()+"\n");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public boolean evaluateCondition(String parentRecord, String childRecord) throws ParseException {
        boolean [] evaluationResults = new boolean [4];
        if (filterElem1 != null){
            evaluationResults[0]=filterElem1.evaluateCondition(parentRecord,childRecord);
        } else{
            if (logicalOp.equalsIgnoreCase(CondOperatorConstants.and)){
                evaluationResults[0] = true;
            } else{
                evaluationResults[0] = false;
            }
        }
        if (filterElem2 != null){
            evaluationResults[1]=filterElem2.evaluateCondition(parentRecord,childRecord);
        } else{
            if (logicalOp.equalsIgnoreCase(CondOperatorConstants.and)){
                evaluationResults[1] = true;
            } else{
                evaluationResults[1] = false;
            }
        }
        if (filterElem3 != null){
            evaluationResults[2]=filterElem3.evaluateCondition(parentRecord,childRecord);
        } else{
            if (logicalOp.equalsIgnoreCase(CondOperatorConstants.and)){
                evaluationResults[2] = true;
            } else{
                evaluationResults[2] = false;
            }
        }
        if (filterElem4 != null){
            evaluationResults[3]=filterElem4.evaluateCondition(parentRecord,childRecord);
        } else{
            if (logicalOp.equalsIgnoreCase(CondOperatorConstants.and)){
                evaluationResults[3] = true;
            } else{
                evaluationResults[3] = false;
            }
        }
        boolean result = false;
        switch (logicalOp.toUpperCase(Locale.ROOT)){
            case CondOperatorConstants.and:
                result = true;
                for (int i=0;i<4;i++){
                    if (!evaluationResults[i]){
                        result = false;
                        break;
                    }
                }
                break;
            case CondOperatorConstants.or:
                result = false;
                for (int i=0;i<4;i++){
                    if (evaluationResults[i]){
                        result = true;
                        break;
                    }
                }
                break;
        }
        return result;
    }

    public static class MultiCondConfigBuilder {
        private String logicalOp;
        private SingleCondConfig filterElem1;
        private SingleCondConfig filterElem2;
        private MultiCondConfig filterElem3;
        private MultiCondConfig filterElem4;

        public MultiCondConfigBuilder setLogicalOp(String logicalOp){
//            System.out.println("Multi Condition logical op : "+logicalOp);
            this.logicalOp = logicalOp;
            return this;
        }

        public MultiCondConfigBuilder setSingleCond(SingleCondConfig singleFltrElem){
            if (this.filterElem1 == null){
                this.filterElem1 = singleFltrElem;
            } else if (this.filterElem2 == null){
                this.filterElem2 = singleFltrElem;
            }
            return this;
        }
        public MultiCondConfigBuilder setMultiCond(MultiCondConfig multiFltrElem){
            if (this.filterElem3 == null){
                this.filterElem3 = multiFltrElem;
            } else if (this.filterElem4 == null){
                this.filterElem4 = multiFltrElem;
            }
            return this;
        }

        public MultiCondConfig build(){
            return new MultiCondConfig(this.logicalOp,this.filterElem1,this.filterElem2,this.filterElem3,this.filterElem4);
        }
    }

}
