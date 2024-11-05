package com.example.pythontest;

public class SampleBarData {
    String trait;
    float traitval;

    public SampleBarData(String trait, float traitval) {
        this.trait = trait;
        this.traitval = traitval;
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public float getTraitval() {
        return traitval;
    }

    public void setTraitval(int traitval) {
        this.traitval = traitval;
    }
}
