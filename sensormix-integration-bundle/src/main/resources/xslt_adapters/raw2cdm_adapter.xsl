<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:s="http://developers.google.com/gdgfirenze/ns/service" xmlns:m="http://developers.google.com/gdgfirenze/ns/model"
	xmlns:fn="http://www.w3.org/2005/xpath-functions" version="1.0">

	<!-- this param is enriched by camel route -->
	<xsl:param name="messageXmlTime" />

	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/root">
		<s:data>
			<s:samples xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:for-each select="sample">
					<xsl:variable name="deviceId" select="normalize-space(device_id)" />
					<xsl:variable name="sampleTime" select="$messageXmlTime" />
					<xsl:if test="time">
						<xsl:variable name="sampleTime"
							select="concat(substring-before(time, ' '),'T',substring-after(time, ' '))" />
					</xsl:if>


					<xsl:if test="battery_level">
						<s:numericValueSample>
							<xsl:attribute name="sensorId"><xsl:value-of
								select="$deviceId" /></xsl:attribute>
							<xsl:attribute name="time"><xsl:value-of
								select="$sampleTime" /></xsl:attribute>
							<xsl:attribute name="type">urn:rixf:net.sensormix/sample_types/battery_level</xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of
								select="battery_level" /></xsl:attribute>
						</s:numericValueSample>
					</xsl:if>

					<xsl:if test="temp">
						<s:numericValueSample>
							<xsl:attribute name="sensorId"><xsl:value-of
								select="$deviceId" /></xsl:attribute>
							<xsl:attribute name="time"><xsl:value-of
								select="$sampleTime" /></xsl:attribute>
							<xsl:attribute name="type">urn:rixf:net.sensormix/sample_types/temp</xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of
								select="temp" /></xsl:attribute>
						</s:numericValueSample>
					</xsl:if>

					<xsl:if test="lux">
						<s:numericValueSample>
							<xsl:attribute name="sensorId"><xsl:value-of
								select="$deviceId" /></xsl:attribute>
							<xsl:attribute name="time"><xsl:value-of
								select="$sampleTime" /></xsl:attribute>
							<xsl:attribute name="type">urn:rixf:net.sensormix/sample_types/light</xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of
								select="lux" /></xsl:attribute>
						</s:numericValueSample>
					</xsl:if>

					<xsl:if test="position">
						<s:positionSample>
							<xsl:attribute name="sensorId"><xsl:value-of
								select="$deviceId" /></xsl:attribute>
							<xsl:attribute name="time">
							<xsl:value-of
								select="concat(substring-before(position/time, ' '),'T',substring-after(position/time, ' '))" />
						</xsl:attribute>
							<xsl:attribute name="type">urn:rixf:net.sensormix/sample_types/phone_gps</xsl:attribute>
							<xsl:attribute name="accuracy"><xsl:value-of
								select="position/accuracy" /></xsl:attribute>
							<xsl:attribute name="alt"><xsl:value-of
								select="position/alt" /></xsl:attribute>
							<xsl:attribute name="bearing"><xsl:value-of
								select="position/bearing" /></xsl:attribute>
							<xsl:attribute name="lat"><xsl:value-of
								select="position/lat" /></xsl:attribute>
							<xsl:attribute name="lng"><xsl:value-of
								select="position/lng" /></xsl:attribute>
							<xsl:attribute name="speed"><xsl:value-of
								select="position/speed" /></xsl:attribute>
						</s:positionSample>
					</xsl:if>

					<xsl:if test="wifi_scans">
						<xsl:for-each select="wifi_scans/item">
							<s:wifiSignalSample>
								<xsl:attribute name="sensorId"><xsl:value-of
									select="$deviceId" /></xsl:attribute>
								<xsl:attribute name="time"><xsl:value-of
									select="$sampleTime" /></xsl:attribute>
								<xsl:attribute name="type">urn:rixf:net.sensormix/sample_types/wifi_signal</xsl:attribute>
								<xsl:attribute name="bssid"><xsl:value-of
									select="bssid" /></xsl:attribute>
								<xsl:attribute name="capabilities"><xsl:value-of
									select="capabilities" /></xsl:attribute>
								<xsl:attribute name="frequency"><xsl:value-of
									select="frequency" /></xsl:attribute>
								<xsl:attribute name="level"><xsl:value-of
									select="level" /></xsl:attribute>
								<xsl:attribute name="ssid"><xsl:value-of
									select="ssid" /></xsl:attribute>
							</s:wifiSignalSample>
						</xsl:for-each>
					</xsl:if>

					<xsl:if test="nfc">
						<s:stringValueSample>
							<xsl:attribute name="sensorId"><xsl:value-of
								select="$deviceId" /></xsl:attribute>
							<xsl:attribute name="time"><xsl:value-of
								select="$sampleTime" /></xsl:attribute>
							<xsl:attribute name="type">urn:rixf:net.sensormix/sample_types/nfc</xsl:attribute>
							<xsl:attribute name="value"><xsl:value-of
								select="nfc" /></xsl:attribute>
						</s:stringValueSample>
					</xsl:if>
				</xsl:for-each>
			</s:samples>

			<xsl:if test="sensor">
				<s:sensor xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
					<xsl:attribute name="id"><xsl:value-of
						select="sensor/id" /></xsl:attribute>
					<xsl:attribute name="type"><xsl:value-of
						select="sensor/type" /></xsl:attribute>
					<xsl:attribute name="name"><xsl:value-of
						select="sensor/name" /></xsl:attribute>
					<xsl:attribute name="description"><xsl:value-of
						select="sensor/description" /></xsl:attribute>
				</s:sensor>
			</xsl:if>
		</s:data>
	</xsl:template>
</xsl:stylesheet>