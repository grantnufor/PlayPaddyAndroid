package HttpAdapter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class GameUserAnswerHttpServiceAdapter {

    String SOAP_ADDRESS ="http://playpaddy.chisomanuforom.com/WebService/GameUserAnswerServices.asmx";

    public ArrayList<JSONObject> GetAllGameUserAnswers()
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetAllGameUserAnswers";
        String OPERATION_NAME_GetJsonData = "GetAllGameUserAnswers";


        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";



        SoapPrimitive response=null;

        ArrayList<JSONObject> obj = new ArrayList<JSONObject>();//create arraylist of jsonobject to capture all returned objects

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = (SoapPrimitive) envelope.getResponse();




            if(response.toString().length()>0 )
            {
                //String responseModified = "["+response+"]";

                JSONArray aryJSONStrings  = new JSONArray(response.toString());
                JSONObject jsonObj = new JSONObject();



                for(int i=0; i<aryJSONStrings.length(); i++) {

                    jsonObj = (JSONObject)aryJSONStrings.getJSONObject(i);

                    obj.add(jsonObj);

                }

            }
            else
            {
                obj = null;
            }

        }
        catch (Exception exception)
        {
            obj = null;
            exception.printStackTrace();
            //Toast.makeTextundefinedthis, exception.printStackTraceundefined) ,Toast.LENGTH_LONG).showundefined);
        }

        return obj;

    }





    public JSONObject GetGameUserAnswerByGameUserAnswerId(String gameUserAnswerId)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetGameUserAnswerByGameUserAnswerId";
        String OPERATION_NAME_GetJsonData = "GetGameUserAnswerByGameUserAnswerId";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";


        SoapPrimitive response=null;

        JSONObject jsonObj = new JSONObject();

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("gameUserAnswerId", gameUserAnswerId);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = (SoapPrimitive) envelope.getResponse();
            jsonObj = new JSONObject(response.toString());



        }
        catch (Exception exception)
        {
            //response="error: "+exception.toString();
            exception.printStackTrace();

        }

        return jsonObj;

    }



    public JSONObject GetGameUserAnswerByGameQuestionId(String gameQuestionId)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetGameUserAnswerByGameQuestionId";
        String OPERATION_NAME_GetJsonData = "GetGameUserAnswerByGameQuestionId";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";


        SoapPrimitive response=null;

        JSONObject jsonObj = new JSONObject();

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("gameQuestionId", gameQuestionId);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = (SoapPrimitive) envelope.getResponse();
            jsonObj = new JSONObject(response.toString());



        }
        catch (Exception exception)
        {
            //response="error: "+exception.toString();
            exception.printStackTrace();

        }

        return jsonObj;

    }




    public String AddGameUserAnswer(String gameQuestionId, String userId, String userAnswer, String correct,
                                    String position, String timeSubmitted, String dateSubmitted)
    {

        String SOAP_ACTION_GetJsonData ="http://tempuri.org/AddGameUserAnswer";
        String OPERATION_NAME_GetJsonData = "AddGameUserAnswer";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";


        SoapPrimitive response= null;
        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);

            request.addProperty("gameQuestionId", gameQuestionId);
            request.addProperty("userId", userId);
            request.addProperty("userAnswer", userAnswer);
            request.addProperty("correct", correct);
            request.addProperty("position", position);
            request.addProperty("timeSubmitted", timeSubmitted);
            request.addProperty("dateSubmitted", dateSubmitted);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = (SoapPrimitive) envelope.getResponse();
            //JSONObject mainJson = new JSONObject(response.toString());

//            ///JSONArray jsonArray = mainJson.getJSONArrayundefined"student");
//            if(response.toString().length()>0 )
//            {
//
//                String responseModified = "["+response+"]";
//
//                JSONArray aryJSONStrings  = new JSONArray(responseModified.toString());
//                JSONObject jsonObj = new JSONObject();
//
////		    int SNO=0;
//
//                for(int i=0; i<aryJSONStrings.length(); i++) {
//
//                    jsonObj = (JSONObject)aryJSONStrings.getJSONObject(i);
//
//
//                }
//
//                response = jsonObj.get("Email");
//
//            }
//            else
//            {
//                response="Empty";
//            }

        }
        catch (Exception exception)
        {
            // response="error: "+exception.toString();
            exception.printStackTrace();
            //Toast.makeTextundefinedthis, exception.printStackTraceundefined) ,Toast.LENGTH_LONG).showundefined);
        }

        return response.toString();

    }



    public String UpdateGameUserAnswer (String gameUserAnswerId, String gameQuestionId, String userId, String userAnswer, String correct,
                                   String position, String timeSubmitted, String dateSubmitted)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/UpdateGameUserAnswer";
        String OPERATION_NAME_GetJsonData = "UpdateGameUserAnswer";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";


        SoapPrimitive response=null;
        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);

            request.addProperty("gameUserAnswerId", gameUserAnswerId);
            request.addProperty("gameQuestionId", gameQuestionId);
            request.addProperty("userId", userId);
            request.addProperty("userAnswer", userAnswer);
            request.addProperty("correct", correct);
            request.addProperty("position", position);
            request.addProperty("timeSubmitted", timeSubmitted);
            request.addProperty("dateSubmitted", dateSubmitted);



            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = (SoapPrimitive)envelope.getResponse();
            //JSONObject mainJson = new JSONObject(response.toString());

//            ///JSONArray jsonArray = mainJson.getJSONArrayundefined"student");
//            if(response.toString().length()>0 )
//            {
//
//                String responseModified = "["+response+"]";
//
//                JSONArray aryJSONStrings  = new JSONArray(responseModified.toString());
//                JSONObject jsonObj = new JSONObject();
//
////		    int SNO=0;
//
//                for(int i=0; i<aryJSONStrings.length(); i++) {
//
//                    jsonObj = (JSONObject)aryJSONStrings.getJSONObject(i);
//
//
//                }
//
//                response = jsonObj.get("Email");
//
//            }
//            else
//            {
//                response="Empty";
//            }

        }
        catch (Exception exception)
        {
            // response="error: "+exception.toString();
            exception.printStackTrace();
            //Toast.makeTextundefinedthis, exception.printStackTraceundefined) ,Toast.LENGTH_LONG).showundefined);
        }

        return response.toString();

    }

}
