# Struts2 Thymeleaf Plug-in

A Struts2 plug-in for using the [Thymeleaf](http://www.thymeleaf.org) templating engine.

## Example Usage

The examples below show you how to map an action's result to a Thymeleaf
template, as well as how to reference the Struts2 action from within the template.

### Action Mapping

    <action name="home" class="com.example.HelloWorldAction">
        <result name="success" type="thymeleaf">/WEB-INF/templates/hello.html</result>
    </action>

### Action Class

    public class HelloWorldAction extends ActionSupport {
      private String message;

      @Override
      public String execute() throws Exception {
        message = "Hello, this is a Thymeleaf example!";

        return SUCCESS;
      }

      public String getMessage() {
        return message;
      }
    }

### Thymeleaf Template

You can refer to properties on the action using the `${action.property}` syntax.
The following template displays the message property of the action.

    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

    <html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>Hello World</title>
        <meta content="text/html; charset=UTF-8" http-equiv="Content-Type"/>
    </head>

    <body>

    <h1 th:text="#{home.welcome}">Welcome</h1>
    <p th:text="${action.message}">This message is only seen during prototyping.</p>

    </body>

    </html>

## Message Resolution

This plugin will cause Thymeleaf to look in the Struts2 i18n resource bundles
to resolve messages.

## License

    Copyright 2013 Steven Benitez.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.