<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:aman="http://www.parlament.gov.rs/schema/amandman"
        xmlns:elem="http://www.parlament.gov.rs/schema/elementi"
        xmlns:str="http://exslt.org/strings"
        extension-element-prefixes="str"
        version="2.0">

    <xsl:variable name="amendmentRef" select="'#'"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Amandmani</title>
                <style>
                    ol.podtacka li {
                    list-style: none;
                    counter-increment: myIndex;
                    }

                    ol.podtacka li:before{
                    content:"("counter(myIndex)") ";
                    }
                </style>
            </head>
            <body style="font-family: Arial;">
                <xsl:apply-templates select="aman:amandmani/aman:head"/>
                <xsl:apply-templates select="aman:amandmani/aman:body"/>
            </body>
        </html>
    </xsl:template>

    <!-- Reading data from head -->
    <xsl:template match="aman:amandmani/aman:head">
        <p>
            <span style="width: 200px;"><b>Datum prijedloga:</b></span>
            <xsl:value-of select="aman:datum_predloga"/>
        </p>
        <p>
            <span style="width: 200px;"><b>Datum izglasavanja:</b></span>
            <xsl:value-of select="aman:datum_izglasavanja"/>
        </p>
        <p>
            <span style="width: 200px;"><b>Mjesto:</b></span>
            <xsl:value-of select="aman:mjesto"/>
        </p>
    </xsl:template>
    <!-- End reading header data -->

    <xsl:template match="aman:amandmani/aman:body">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="aman:pravni_osnov">
        <h3 style="font-size: 12pt; text-align: center;">PRAVNI OSNOV</h3>
        <xsl:apply-templates/>
    </xsl:template>

    <!-- Write head to new amendment -->
    <xsl:template match="aman:amandman/aman:head/aman:predmet">
        <h3 style="font-size: 12pt; text-align: center;">AMANDMAN ZA DOPUNU ZAKONA</h3>
        <xsl:copy>
            <xsl:copy-of select="@*" />
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>

    <!-- Skip 'rjesenje' -->
    <xsl:template match="aman:amandman/aman:head/aman:rjesenje">

    </xsl:template>

    <xsl:template match="aman:amandman/aman:head/aman:predmet/elem:ref">
        <xsl:variable name="refFull" select="current()/@id"/>
        <xsl:variable name="refSplitted" select="str:tokenize(current()/@id, '_')"/>

        <xsl:variable name="amendmentRef" select="concat($refSplitted[1], '_', $refSplitted[2], '_', $refSplitted[3], '#', $refFull)"/>

        <p>
            Amandman se odnosi na element koji možete vidjeti
            <a>
                <xsl:attribute name="href">http://localhost:9000/api/laws/<xsl:value-of select="$amendmentRef"/></xsl:attribute>
                ovdje</a>.
        </p>
    </xsl:template>


    <xsl:template match="aman:odredba">
        <h5 style="font-weight: bold; font-size: 11pt">Odredba o dopuni/izmjeni zakona</h5>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="aman:obrazlozenje">
        <h3 style="font-size: 14pt; font-weight: bold; text-align: center;">O b r a z l o ž e n j e</h3>
        <xsl:apply-templates/>
    </xsl:template>

    <!-- Elementi za obrazlozenje amandmana -->
    <xsl:template match="aman:razlog">
        <h5 style="font-weight: bold;font-size: 11pt">I. Razlog podnošenja amandmana</h5>
        <p style="margin-top: -8px;">
            <xsl:apply-templates/>
        </p>
    </xsl:template>

    <xsl:template match="aman:objasnjene_predlozenog_rjesenja">
        <h5 style="font-weight: bold; font-size: 11pt">II. Objašnjenje predloženog rješenja</h5>
        <p style="margin-top: -8px;">
            <xsl:apply-templates/>
        </p>
    </xsl:template>

    <xsl:template match="aman:cilj">
        <h5 style="font-weight: bold; font-size: 11pt">III. Cilj</h5>
        <p style="margin-top: -8px;">
            <xsl:apply-templates/>
        </p>
    </xsl:template>

    <xsl:template match="aman:uticaj_na_budzetska_sredstva">
        <h5 style="font-weight: bold; font-size: 11pt">IV. Uticaj na budžetska sredstva</h5>
        <p style="margin-top: -8px;">
            <xsl:apply-templates/>
        </p>
    </xsl:template>

    <!-- Elementi koji se mogu naci u odredbi amandmana -->
    <xsl:template match="elem:clan">
        <h6 style="font-weight: bold; text-align: center; font-size: 11pt">
            <xsl:value-of select="@name"/>
        </h6>
        <h6 style="text-align: center; font-size: 11pt">
            <i>Član <xsl:number format="1." level="any" count="elem:clan"/></i>
        </h6>
        <xsl:apply-templates select="elem:stav"/>
    </xsl:template>

    <xsl:template match="elem:stav">
        <p style="font-size: 11pt; text-align: justify">
            <xsl:value-of select="text()"/>
        </p>
        <xsl:apply-templates select="elem:tacka"/>
    </xsl:template>

    <xsl:template match="elem:tacka">
        <ol>
            <li style="font-size: 11pt; text-align: justify">
                <xsl:value-of select="text()"/>

                <xsl:apply-templates/>
            </li>
        </ol>
    </xsl:template>

    <xsl:template match="elem:podtacka">
        <ol class="podtacka">
            <li style="font-size: 11pt; text-align: justify">
                <xsl:value-of select="text()"/>
                <xsl:apply-templates/>
            </li>
        </ol>
    </xsl:template>

    <xsl:template match="elem:alineja">
        <ul>
            <li style="font-size: 10pt; text-align: justify; list-style: none;">
                - <xsl:value-of select="text()"/>
            </li>
        </ul>
    </xsl:template>

    <!-- Override mixed content with link in XHTML -->

    <!-- Razlog -->
    <xsl:template match="aman:razlog//*">
        <xsl:copy>
            <xsl:copy-of select="@*" />
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="aman:razlog//elem:ref">
        <a>
            <xsl:attribute name="href">http://localhost:9000/api/laws/<xsl:value-of select="substring-before(current()/@id, '/')"/></xsl:attribute>
            <xsl:apply-templates/>
        </a>
    </xsl:template>

    <!-- Objasnjenje -->
    <xsl:template match="aman:objasnjene_predlozenog_rjesenja//*">
        <xsl:copy>
            <xsl:copy-of select="@*" />
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="aman:objasnjene_predlozenog_rjesenja//elem:ref">
        <a>
            <xsl:attribute name="href">http://localhost:9000/api/laws/<xsl:value-of select="substring-before(current()/@id, '/')"/></xsl:attribute>
            <xsl:apply-templates/>
        </a>
    </xsl:template>

    <!-- Cilj -->
    <xsl:template match="aman:cilj//*">
        <xsl:copy>
            <xsl:copy-of select="@*" />
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="aman:cilj//elem:ref">
        <a>
            <xsl:attribute name="href">http://localhost:9000/api/laws/<xsl:value-of select="substring-before(current()/@id, '/')"/></xsl:attribute>
            <xsl:apply-templates/>
        </a>
    </xsl:template>

    <!-- Uticaj na budzetska sredstva -->
    <xsl:template match="aman:uticaj_na_budzetska_sredstva//*">
        <xsl:copy>
            <xsl:copy-of select="@*" />
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="aman:uticaj_na_budzetska_sredstva//elem:ref">
        <a>
            <xsl:attribute name="href">http://localhost:9000/api/laws/<xsl:value-of select="substring-before(current()/@id, '/')"/></xsl:attribute>
            <xsl:apply-templates/>
        </a>
    </xsl:template>

</xsl:stylesheet>