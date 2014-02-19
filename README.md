Bonita-Tools
============

A set of tools and methods to use with Bonita BPM

Working with URLs
-----------------
##### Useful when you want to send a mail whit a link to a form
 - How to get a task form url?
> String url = BonitaURLTools.getHumanTaskURL("http://localhost:8080",activityInstanceId, apiAccessor)

 - How to get a case initialization form url?
> String url = BonitaURLTools.getStartProcessCaseURL("http://localhost:8080",processDefinitionId, apiAccessor)

 - How to get a case initialization form url in AUTOLOGIN mode?
> String url = BonitaURLTools.getStartProcessCaseURLWithAutologin("http://localhost:8080",processDefinitionId, apiAccessor)

Working with java.sql.ResultSet objects
-----------------
##### Useful to transform a database connector output into data ready to use in select, list, table or editable widgets
 
 - How to get a map of elements ready to use into a SELECT widget?
> Map<Object, Object> mapElements = BonitaDBTools.toKeyValueMap(resultSet, "column name to be considerated as KEY", "column name to be considerated as displayed LABEL")

 - How to get a list of elements ready to use into a SELECT or LIST widget?
> List<Object> listElements = BonitaDBTools.toList(resultSet, "column name to be considerated as displayed LABEL")

 - How to get the first element of a query?
> Object element = BonitaDBTools.getObject(resultSet)

 - How to get a map ready to bind to a simple GROUP widget?
> List mapToBindIntoGroup = BonitaDBTools.toListOfMap(resultSet)

 - How to get a list of maps ready to bind to a multiple GROUP widget?
> List listOfMapToBindIntoGroup = BonitaDBTools.toListOfMap(resultSet)

 - How to get a list of lists ready to bind to a TABLE or GRID widget?
> List listOfList = BonitaDBTools.toListOfList(resultSet)
