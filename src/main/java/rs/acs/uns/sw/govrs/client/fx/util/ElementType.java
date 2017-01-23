package rs.acs.uns.sw.govrs.client.fx.util;

public enum ElementType {
    Law {
        public String toString() {
            return "law";
        }
    },
    Chapter {
        public String toString() {
            return "chapter";
        }
    }, Part {
        public String toString() {
            return "part";
        }
    }, Section {
        public String toString() {
            return "section";
        }
    }, Subsection {
        public String toString() {
            return "subsection";
        }
    }, Article {
        public String toString() {
            return "article";
        }
    }, Paragraph {
        public String toString() {
            return "paragraph";
        }
    }, Clause {
        public String toString() {
            return "clause";
        }
    }, Subclause {
        public String toString() {
            return "subclause";
        }
    }, Item {
        public String toString() {
            return "item";
        }
    }, Amendment {
        public String toString() {
            return "amendment";
        }
    }, Amendments {
        public String toString() {
            return "amendments";
        }
    }, None
}
