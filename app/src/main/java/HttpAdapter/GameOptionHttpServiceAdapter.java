package HttpAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class GameOptionHttpServiceAdapter {


    public ArrayList<JSONObject> GetAllGameOptions()
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetAllGameOptions";
        String OPERATION_NAME_GetJsonData = "GetAllGameOptions";


        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/GameOptionServices.asmx";


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





    public JSONObject GetGameOptionByGameOptionId(String gameOptionId)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetGameOptionByGameOptionId";
        String OPERATION_NAME_GetJsonData = "GetGameOptionByGameOptionId";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/GameOptionServices.asmx";

        SoapPrimitive response=null;

        JSONObject jsonObj = new JSONObject();

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("gameQuestionId", gameOptionId);
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



    public JSONObject GetGameOptionByGameQuestionIdAndOptionName(String gameQuestionId, String optionName)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetGameOptionByGameQuestionIdAndOptionName";
        String OPERATION_NAME_GetJsonData = "GetGameOptionByGameQuestionIdAndOptionName";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/GameOptionServices.asmx";

        SoapPrimitive response=null;

        JSONObject jsonObj = new JSONObject();

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("gameQuestionId", gameQuestionId);
            request.addProperty("optionName", optionName);
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




    public String AddGameOption(String optionName, String optionDetail, String image, String gameQuestionId)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/AddGameOption";
        String OPERATION_NAME_GetJsonData = "AddGameOption";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";



        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/GameOptionServices.asmx";

        SoapPrimitive response=null;
        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("optionName", optionName);
            request.addProperty("optionDetail", optionDetail);
            request.addProperty("image", image);
            request.addProperty("gameQuestionId", gameQuestionId);

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



    public String UpdateGameOption(String gameOptionId, String optionName, String optionDetail, String image, String gameQuestionId )
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/UpdateGameOption";
        String OPERATION_NAME_GetJsonData = "UpdateGameOption";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";



        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/GameOptionServices.asmx";

        SoapPrimitive response=null;
        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("gameOptionId", gameOptionId);
            request.addProperty("optionName", optionName);
            request.addProperty("optionDetail", optionDetail);
            request.addProperty("image", image);
            request.addProperty("gameQuestionId", gameQuestionId);



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
