public class HYPJSON
{
private static List<String> parseJSONArray(String text)
    {
        if(text == null)
            return null;
        try
        {
            JSONArray jsonArray = new JSONArray(text);
            List<String> result = new ArrayList<String>();
            for(int index = 0; index < jsonArray.length(); index++)
            {
                result.add(index,jsonArray.get(index).toString());
            }
            return result;
        }
        catch (JSONException e)
        {
            return null;
        }

    }

private static Map<String,String> parseJSONObject(String response)
    {
        if(response == null)
            return null;
        try {
            final Map<String, String> result = new HashMap<>();
            JSONObject obj = new JSONObject(response);
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String val = obj.getString(key);
                result.put(key, val);
            }
            return result;
        } catch (JSONException e)
        {
            return null;
        }
    }

    //assumption for JSON array: ordered
    //assumption for JSON object: unordered
    private static boolean deepCompareJSON(String jsonStringA,String jsonStringB) {
        if (jsonStringA == null || jsonStringB == null) {
            return (jsonStringA == null && jsonStringB == null);
        }
        Map<String, String> jsonMapA = parseJSONObject(jsonStringA);
        Map<String, String> jsonMapB = parseJSONObject(jsonStringB);
        if (jsonMapA == null || jsonMapB == null) {
            if (jsonMapA == null && jsonMapB == null) {
                List<String> arrayA = parseJSONArray(jsonStringA);
                List<String> arrayB = parseJSONArray(jsonStringB);
                if (arrayA == null || arrayB == null)
                    return arrayA == null && arrayB == null && jsonStringA.equals(jsonStringB);
                if (arrayA.equals(arrayB))
                    return true;
                else
                {
                    //getting here probably means an array of objects and objects are not ordered
                    final int size = arrayA.size();
                    for (int i = 0; i < size; i++)
                    {
                        if (!deepCompareJSON(arrayA.get(i), arrayB.get(i)))
                            return false;
                    }
                    return true;
                }
            } else
                return false;
        }

        if (jsonMapA.size() != jsonMapB.size())
            return false;
        for (Map.Entry<String, String> eachMap : jsonMapA.entrySet())
        {
            final String jsonB = jsonMapB.get(eachMap.getKey());
            if (jsonB == null)
                return false;
            else if (!deepCompareJSON(eachMap.getValue(), jsonMapB.get(eachMap.getKey())))
                return false;
        }
        return true;
    }
}