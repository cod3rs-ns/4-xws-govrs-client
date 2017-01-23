package rs.acs.uns.sw.govrs.client.fx.serverdomain.enums;

public enum AmendmentType {
    Izmena {
        public String toString() {
            return "izmena";
        }
    },
    Dopuna {
        public String toString() {
            return "dopuna";
        }
    },
    Brisanje {
        public String toString() {
            return "uklanjanje";
        }
    }
}
