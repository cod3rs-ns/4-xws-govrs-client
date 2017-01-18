<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:prop="http://www.parlament.gov.rs/schema/propis"
        xmlns:elem="http://www.parlament.gov.rs/schema/elementi"
        version="2.0">

    <xsl:template match="/">
        <html>
            <head>
                <title>Propis HTML</title>
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
                <xsl:apply-templates select="prop:propis/prop:body"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="elem:dio">
        <h2 style="text-align: center; font-weight: bold;">
            <xsl:attribute name="id">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:value-of select="@name" />
        </h2>
        <xsl:apply-templates select="elem:glava"/>
    </xsl:template>

    <xsl:template match="elem:glava">
        <h2 style="text-align: center; font-weight: bold;">
            <xsl:attribute name="id">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:value-of select="@name" />
        </h2>
        <xsl:apply-templates select="elem:odjeljak"/>
    </xsl:template>

    <xsl:template match="elem:odjeljak">
        <h3 style="font-weight: bold; text-align: center; font-size: 14pt">
            <xsl:attribute name="id">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:value-of select="position()"/>. <xsl:value-of select="@name"/>
        </h3>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="elem:pododjeljak">
        <h4 style="font-weight: bold; text-align: center; font-size: 12pt">
            <xsl:attribute name="id">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:number format="a" select="position()"/>) <xsl:value-of select="@name"/>
        </h4>
        <xsl:apply-templates select="elem:clan"/>
    </xsl:template>

    <xsl:template match="elem:clan">
        <h6 style="font-weight: bold; text-align: center; font-size: 12pt">
            <xsl:value-of select="@name"/>
        </h6>
        <h6 style="text-align: center; font-size: 12pt">
            <xsl:attribute name="id">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <i>Član <xsl:number format="1." level="any" count="elem:clan"/></i>
        </h6>
        <xsl:apply-templates select="elem:stav"/>
    </xsl:template>

    <xsl:template match="elem:stav">
        <p style="font-size: 11pt; text-align: justify">
            <xsl:attribute name="id">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:value-of select="current()"/>
        </p>
        <xsl:if test="elem:tacka">
            <ol>
                <xsl:for-each select="elem:tacka">
                    <li style="font-size: 11pt; text-align: justify">
                        <xsl:attribute name="id">
                            <xsl:value-of select="@id"/>
                        </xsl:attribute>
                        <xsl:value-of select="current()"/>
                        <xsl:if test="elem:podtacka">
                            <ol class="podtacka">
                                <xsl:for-each select="elem:podtacka">
                                    <li style="font-size: 11pt; text-align: justify">
                                        <xsl:attribute name="id">
                                            <xsl:value-of select="@id"/>
                                        </xsl:attribute>
                                        <xsl:value-of select="current()"/>
                                        <xsl:apply-templates select="elem:alineja"/>
                                    </li>
                                </xsl:for-each>
                            </ol>
                        </xsl:if>
                        <xsl:apply-templates select="elem:alineja"/>
                    </li>
                </xsl:for-each>
            </ol>
        </xsl:if>
    </xsl:template>

    <xsl:template match="elem:alineja">
        <p style="margin-left: 5em;">
            <xsl:attribute name="id">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            - <xsl:value-of select="current()"/>
        </p>
    </xsl:template>

</xsl:stylesheet>