package DTO;

public class UserData {
    private String pnr, fnamn, enamn, gadress, postort, postnr, telefon, email;

    public UserData(String pnr, String fnamn, String enamn, String gadress, String postort, String postnr, String telefon, String email) {
        this.pnr = pnr;
        this.fnamn = fnamn;
        this.enamn = enamn;
        this.gadress = gadress;
        this.postort = postort;
        this.postnr = postnr;
        this.telefon = telefon;
        this.email = email;
    }

    public String getPnr() {
        return pnr;
    }

    public String getFnamn() {
        return fnamn;
    }

    public String getEnamn() {
        return enamn;
    }

    public String getGadress() {
        return gadress;
    }

    public String getPostort() {
        return postort;
    }

    public String getPostnr() {
        return postnr;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }
}
