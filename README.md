# BookSearchApp
<br>
  <b>Application features:</b>
  <li>Uses amazon.com to find books for given title.</li>
  <li>Concurrently downloads of result web pages and parses them for books details.</li>
  <li>Presents the results in GUI.</li>
  
<br>
  <b>Project assumptions:</b>
  <li>There are no API's.</li>
  <li>There is no other source where I can get the data from (no databases,
  feeds and such).</li>
  <li>There is no access to the source files (data from public web sites).</li>
  <li>Let's say the data is normal text, displayed in a <code>HTML</code>
  page.</li>
  <li>Although there are <code>HTML</code> parsing libraries like
  <code>jsoup</code> for practice reason they won't be used.</li>
  <li>In the case of <code>HTML</code> parsing, I know that there is no actual
  stable way to get the data. As soon as the page changes, your parser is done
  for.</li>
  
  <br>
  <b>Allowed parameters format:</b>
  <li>parameter: <b>title</b> - <code>search for this "title"</code></li>
  <li>parameter: <b>multipart title</b> -
  <code>search for this "multipart title"</code></li>
  <li>parameter: <b>-t title</b> -
  <code>use previous search results of "title"</code></li>
  <li>parameter: <b>-t multipart title</b> -
  <code>use previous search results of "multipart title"</code></li>
  
  <br>
  <b> Example: </b>
  <li><code>java Launcher -t java</code> - use previous saved search for "java"
  title</li>
  <li><code>java Launcher sql for beginers</code> - search for "sql for
  beginners" title</li>
  
  <br>
  <b> Demo: </b>  <br>
 
 ![Demo](https://github.com/FStefanski/BookSearchApp/blob/master/resources/images/BookSearchApp.gif)

  
