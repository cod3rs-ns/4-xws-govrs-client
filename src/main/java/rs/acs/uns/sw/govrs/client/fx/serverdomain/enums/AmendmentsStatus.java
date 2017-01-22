package rs.acs.uns.sw.govrs.client.fx.serverdomain.enums;


public enum AmendmentsStatus {
    Predlozen {
        public String toString() {
            return "predložen";
        }
    },
    Prihvacen {
        public String toString() {
            return "prihvaćen";
        }
    },
    Odbijen {
        public String toString() {
            return "odbijen";
        }
    }
}
