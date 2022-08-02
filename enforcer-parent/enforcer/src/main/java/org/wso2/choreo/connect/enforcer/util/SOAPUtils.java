/*
 * Copyright (c) 2022, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.choreo.connect.enforcer.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wso2.choreo.connect.enforcer.commons.logging.ErrorDetails;
import org.wso2.choreo.connect.enforcer.commons.logging.LoggingConstants;
import org.wso2.choreo.connect.enforcer.constants.APIConstants;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

/**
 * sample soap message
 */
public class SOAPUtils {
    private static final Logger log = LogManager.getLogger(SOAPUtils.class);

    public static String getSoapFaultMessage(String soapProtocolVersion,
                                             String message, String description, String code) {
        try {
            MessageFactory factory = MessageFactory.newInstance(soapProtocolVersion);
            SOAPMessage soapMsg = factory.createMessage();
            SOAPPart part = soapMsg.getSOAPPart();
            SOAPEnvelope envelope = part.getEnvelope();
            SOAPFault soapFault = envelope.getBody().addFault();
            if (soapProtocolVersion.equals(APIConstants.SOAP11_PROTOCOL)) {
                soapFault.setFaultCode("soapenv:Server");
            } else if (soapProtocolVersion.equals(APIConstants.SOAP12_PROTOCOL)) {
                soapFault.setFaultCode("soapenv:Receiver");
            }
            soapFault.setFaultString(message);
            soapFault.addDetail().addTextNode(code + ":" + description);
            return envelope.toString();
        } catch (Exception e) {
            log.error("Error while creating the SOAP fault message.",
                    ErrorDetails.errorLog(LoggingConstants.Severity.MINOR, 7101), e);
            return "";
        }
    }
}
