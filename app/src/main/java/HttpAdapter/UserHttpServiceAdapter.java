package HttpAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class UserHttpServiceAdapter {


    String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebServices/UserServices.asmx";

    public JSONObject GetUserByEmailAndPassword(String email, String password)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetUserByEmailAndPassword";
        String OPERATION_NAME_GetJsonData = "GetUserByEmailAndPassword";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/UserServices.asmx";

        SoapPrimitive response=null;

        JSONObject jsonObj = new JSONObject();

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("email", email);
            request.addProperty("password", password);
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


    public JSONObject GetUserByPhoneNumberAndPassword(String phoneNumber, String password)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetUserByPhoneNumberAndPassword";
        String OPERATION_NAME_GetJsonData = "GetUserByPhoneNumberAndPassword";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/UserServices.asmx";

        SoapPrimitive response=null;

        JSONObject jsonObj = new JSONObject();

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("phoneNumber", phoneNumber);
            request.addProperty("password", password);
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


    public JSONObject GetUserByPhoneNumber(String phoneNumber)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetUserByPhoneNumber";
        String OPERATION_NAME_GetJsonData = "GetUserByPhoneNumber";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/UserServices.asmx";

        Object response=null;

        JSONObject jsonObj = new JSONObject();

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("phoneNumber", phoneNumber);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = envelope.getResponse();
            //JSONObject mainJson = new JSONObject(response.toString());

            ///JSONArray jsonArray = mainJson.getJSONArrayundefined"student");
            if(response.toString().length()>0 )
            {
//
                String responseModified = "["+response+"]";

                JSONArray aryJSONStrings  = new JSONArray(responseModified.toString());
                //JSONObject jsonObj = new JSONObject();

//	    int SNO=0;

                for(int i=0; i<aryJSONStrings.length(); i++) {

                    jsonObj = (JSONObject)aryJSONStrings.getJSONObject(i);



                }

                //response = jsonObj.get("Email");

            }
            else
            {
                //response="Empty";
            }

        }
        catch (Exception exception)
        {
            //response="error: "+exception.toString();
            exception.printStackTrace();
            //Toast.makeTextundefinedthis, exception.printStackTraceundefined) ,Toast.LENGTH_LONG).showundefined);
        }

        return jsonObj;

    }


    public JSONObject GetUserByEmail(String email)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetUserByEmail";
        String OPERATION_NAME_GetJsonData = "GetUserByEmail";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/UserServices.asmx";

        Object response=null;

        JSONObject jsonObj = new JSONObject();

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("email", email);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = envelope.getResponse();
            //JSONObject mainJson = new JSONObject(response.toString());

            ///JSONArray jsonArray = mainJson.getJSONArrayundefined"student");
            if(response.toString().length()>0 )
            {
//
                String responseModified = "["+response+"]";

                JSONArray aryJSONStrings  = new JSONArray(responseModified.toString());
                //JSONObject jsonObj = new JSONObject();

//	    int SNO=0;

                for(int i=0; i<aryJSONStrings.length(); i++) {

                    jsonObj = (JSONObject)aryJSONStrings.getJSONObject(i);



                }

                //response = jsonObj.get("Email");

            }
            else
            {
                //response="Empty";
            }

        }
        catch (Exception exception)
        {
            //response="error: "+exception.toString();
            exception.printStackTrace();
            //Toast.makeTextundefinedthis, exception.printStackTraceundefined) ,Toast.LENGTH_LONG).showundefined);
        }

        return jsonObj;

    }


    public JSONObject GetUserByUserName(String userName)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetUserByUserName";
        String OPERATION_NAME_GetJsonData = "GetUserByUserName";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/UserServices.asmx";

        Object response=null;

        JSONObject jsonObj = new JSONObject();

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("userName", userName);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = envelope.getResponse();
            //JSONObject mainJson = new JSONObject(response.toString());

            ///JSONArray jsonArray = mainJson.getJSONArrayundefined"student");
            if(response.toString().length()>0 )
            {
//
                String responseModified = "["+response+"]";

                JSONArray aryJSONStrings  = new JSONArray(responseModified.toString());
                //JSONObject jsonObj = new JSONObject();

//	    int SNO=0;

                for(int i=0; i<aryJSONStrings.length(); i++) {

                    jsonObj = (JSONObject)aryJSONStrings.getJSONObject(i);



                }

                //response = jsonObj.get("Email");

            }
            else
            {
                //response="Empty";
            }

        }
        catch (Exception exception)
        {
            //response="error: "+exception.toString();
            exception.printStackTrace();
            //Toast.makeTextundefinedthis, exception.printStackTraceundefined) ,Toast.LENGTH_LONG).showundefined);
        }

        return jsonObj;

    }



    public JSONObject GetUserByUserNameAndPassword(String userName, String password)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetUserByUserNameAndPassword";
        String OPERATION_NAME_GetJsonData = "GetUserByUserNameAndPassword";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/UserServices.asmx";

        Object response=null;

        JSONObject jsonObj = new JSONObject();

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("userName", userName);
            request.addProperty("password", password);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = envelope.getResponse();
            //JSONObject mainJson = new JSONObject(response.toString());

            ///JSONArray jsonArray = mainJson.getJSONArrayundefined"student");
            if(response.toString().length()>0 )
            {
//
                String responseModified = "["+response+"]";

                JSONArray aryJSONStrings  = new JSONArray(responseModified.toString());
                //JSONObject jsonObj = new JSONObject();

//	    int SNO=0;

                for(int i=0; i<aryJSONStrings.length(); i++) {

                    jsonObj = (JSONObject)aryJSONStrings.getJSONObject(i);



                }

                //response = jsonObj.get("Email");

            }
            else
            {
                //response="Empty";
            }

        }
        catch (Exception exception)
        {
            //response="error: "+exception.toString();
            exception.printStackTrace();
            //Toast.makeTextundefinedthis, exception.printStackTraceundefined) ,Toast.LENGTH_LONG).showundefined);
        }

        return jsonObj;

    }


    public JSONObject GetUserByUserId(String userId)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetUserByUserId";
        String OPERATION_NAME_GetJsonData = "GetUserByUserId";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/UserServices.asmx";

        Object response=null;

        JSONObject jsonObj = new JSONObject();

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("userId", userId);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = envelope.getResponse();
            //JSONObject mainJson = new JSONObject(response.toString());

            ///JSONArray jsonArray = mainJson.getJSONArrayundefined"student");
            if(response.toString().length()>0 )
            {
//
                String responseModified = "["+response+"]";

                JSONArray aryJSONStrings  = new JSONArray(responseModified.toString());
                //JSONObject jsonObj = new JSONObject();

//	    int SNO=0;

                for(int i=0; i<aryJSONStrings.length(); i++) {

                    jsonObj = (JSONObject)aryJSONStrings.getJSONObject(i);



                }

                //response = jsonObj.get("Email");

            }
            else
            {
                //response="Empty";
            }

        }
        catch (Exception exception)
        {
            //response="error: "+exception.toString();
            exception.printStackTrace();
            //Toast.makeTextundefinedthis, exception.printStackTraceundefined) ,Toast.LENGTH_LONG).showundefined);
        }

        return jsonObj;

    }




    public ArrayList<JSONObject> GetAllUsers()
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/GetAllUsers";
        String OPERATION_NAME_GetJsonData = "GetAllUsers";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/UserServices.asmx";

        Object response=null;

        ArrayList<JSONObject> obj = new ArrayList<JSONObject>();//create arraylist of jsonobject to capture all returned objects

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = envelope.getResponse();



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




    public String RegisterUser(String userName, String phoneNumber, String email, String password,
                               String dateRegistered, String dateLastModified, String verified )
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/CreateUser";
        String OPERATION_NAME_GetJsonData = "CreateUser";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/UserServices.asmx";


        SoapPrimitive response=null;

        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("userName", userName);
            request.addProperty("phoneNumber", phoneNumber);
            request.addProperty("email", email);
            request.addProperty("password", password);
            request.addProperty("dateRegistered", dateRegistered);
            request.addProperty("dateLastModified", dateLastModified);
            request.addProperty("verified", verified);




            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = (SoapPrimitive)envelope.getResponse();

//		   ///JSONArray jsonArray = mainJson.getJSONArrayundefined"student");
//		   if(response.toString().length()>0 )
//		   {
//
//			   String responseModified = "["+response+"]";
//
//			   JSONArray aryJSONStrings  = new JSONArray(responseModified.toString());
//			   JSONObject jsonObj = new JSONObject();
//
////		    int SNO=0;
//
//		    for(int i=0; i<aryJSONStrings.length(); i++) {
//
//		    	jsonObj = (JSONObject)aryJSONStrings.getJSONObject(i);
//
//
//		    }
//
//		    response = jsonObj.get("Email");
//
//		   }
//		   else
//		   {
//		    response="Empty";
//		   }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();

            //Toast.makeTextundefinedthis, exception.printStackTraceundefined) ,Toast.LENGTH_LONG).showundefined);
        }

        return response.toString();

    }


    public String UpdateUser(long userId, String userName, String phoneNumber, String email, String password,
                             String dateRegistered, String dateLastModified, String verified )
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/UpdateUser";
        String OPERATION_NAME_GetJsonData = "UpdateUser";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/UserServices.asmx";

        SoapPrimitive response=null;
        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("userId", userId);
            request.addProperty("userName", userName);
            request.addProperty("phoneNumber", phoneNumber);
            request.addProperty("email", email);
            request.addProperty("password", password);
            request.addProperty("dateRegistered", dateRegistered);
            request.addProperty("dateLastModified", dateLastModified);
            request.addProperty("verified", verified);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = (SoapPrimitive) envelope.getResponse();
            //JSONObject mainJson = new JSONObject(response.toString());

            ///JSONArray jsonArray = mainJson.getJSONArrayundefined"student");
//		   if(response.toString().length()>0 )
//		   {
////
//			   String responseModified = "["+response+"]";
//
//			   JSONArray aryJSONStrings  = new JSONArray(responseModified.toString());
//			   JSONObject jsonObj = new JSONObject();
//
////		    int SNO=0;
//
//		    for(int i=0; i<aryJSONStrings.length(); i++) {
//
//		    	jsonObj = (JSONObject)aryJSONStrings.getJSONObject(i);
//
//
//
//		    }
//
//		    response = jsonObj.get("Email");
//
//		   }
//		   else
//		   {
//		    response="Empty";
//		   }

        }
        catch (Exception exception)
        {
            // response="error: "+exception.toString();
            exception.printStackTrace();
            //Toast.makeTextundefinedthis, exception.printStackTraceundefined) ,Toast.LENGTH_LONG).showundefined);
        }

        return response.toString();

    }


    public String UpdateUserVerified(String userId,  String verified)
    {
        String SOAP_ACTION_GetJsonData ="http://tempuri.org/UpdateUserVerified";
        String OPERATION_NAME_GetJsonData = "UpdateUserVerified";

        String WSDL_TARGET_NAMESPACE ="http://tempuri.org/";

        String SOAP_ADDRESS ="http://playpaddy.studyair.com/WebService/UserServices.asmx";


        SoapPrimitive response=null;
        try
        {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME_GetJsonData);
            request.addProperty("userId", userId);
            request.addProperty("verified", verified);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
            httpTransport.call(SOAP_ACTION_GetJsonData, envelope);

            response = (SoapPrimitive) envelope.getResponse();
            //JSONObject mainJson = new JSONObject(response.toString());

            ///JSONArray jsonArray = mainJson.getJSONArrayundefined"student");
//		   if(response.toString().length()>0 )
//		   {
////
//			   String responseModified = "["+response+"]";
//
//			   JSONArray aryJSONStrings  = new JSONArray(responseModified.toString());
//			   JSONObject jsonObj = new JSONObject();
//
////		    int SNO=0;
//
//		    for(int i=0; i<aryJSONStrings.length(); i++) {
//
//		    	jsonObj = (JSONObject)aryJSONStrings.getJSONObject(i);
//
//
//
//		    }
//
//		    response = jsonObj.get("Email");
//
//		   }
//		   else
//		   {
//		    response="Empty";
//		   }

        }
        catch (Exception exception)
        {
            //response="error: "+exception.toString();
            exception.printStackTrace();
            //Toast.makeTextundefinedthis, exception.printStackTraceundefined) ,Toast.LENGTH_LONG).showundefined);
        }

        return response.toString();

    }



}
