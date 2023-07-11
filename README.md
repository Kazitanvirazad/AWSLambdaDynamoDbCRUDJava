<h2>AWS DynamoDB CRUD Operations from AWS Lambda Function Using Java17 Runtime </h2>

<h4>AWS Services used:</h4>
<ol>
    <li>AWS API Gateway</li>
    <li>AWS Serverless Lambda</li>
    <li>AWS DynamoDB Database</li>
    <li>Runtime: Java v17</li>
</ol>

<h4>API Documentation - </h4>

<h5><u>Get All Products:</u> </h5>
<p><u>Api Details:</u> Rest API to get all products list from DynamoDB database.</p>
URL endpoint - {hostname}/{stage}/products<br />
http method - "GET"<br />
produces "application/json"
<pre>
responsebody -
{
    "error": false,
    "message": null,
    "data": [
        {
            "productId": "1004",
            "price": 144.0,
            "color": "pink",
            "productName": "Tronz Wireless VR Headset",
            "inventory": "155",
            "properties": {
                "category": "Electronic Gadget",
                "connectionType": "Wireless"
            }
        },
        {
            "productId": "1002",
            "price": 567.0,
            "color": "black",
            "productName": "Tronz Bluetooth Speaker",
            "inventory": "266",
            "properties": {
                "category": "Electronic Gadget",
                "connectionType": "BlueTooth"
            }
        }
    ]
}
</pre>

<h5><u>Get Product By Primary Key Id:</u> </h5>
<p><u>Api Details:</u> Rest API to get a product by Primary Key from DynamoDB database.</p>
URL endpoint - {hostname}/{stage}/products/getproductbyid?productId={IdValue}<br />
http method - "GET"<br />
produces "application/json"
<pre>
responsebody -
{
    "error": false,
    "message": null,
    "data": {
        "productId": "1005",
        "price": 144.0,
        "color": "pink",
        "productName": "Tronz Bluetooth Speaker",
        "inventory": "155",
        "properties": {
            "category": "Electronic Gadget",
            "connectionType": "Wireless"
        }
    }
}
</pre>

<h5><u>Add a new product:</u> </h5>
<p><u>Api Details:</u> Add a new product item to the DynamoDB database table.</p>
URL endpoint - {hostname}/{stage}/products/addproduct<br />
http method - "POST"<br />
produces "application/json"
<pre>
requestbody -
{
    "productId": "1006",
    "price": 233,
    "color": "black",
    "productName": "Tronz Wireless Earphones",
    "inventory": "344",
    "properties": {
        "category": "Electronic Gadget",
        "connectionType": "Bluetooth"
    }
}
responsebody -
{
    "error": false,
    "message": "Item added successfully!",
    "data": null
}
</pre>

<h5><u>Update/Modify a product item:</u> </h5>
<p><u>Api Details:</u> Update/modify any attribute of a product item in the DynamoDB database table and returns updated Product.</p>
URL endpoint - {hostname}/{stage}/products/updateproduct<br />
http method - "PUT"<br />
produces "application/json"
<pre>
<b>Example - 1</b>
requestbody -
{
    "productId": "1005",
    "updateKey": "productName",
    "updateValue": "Tronz Wireless VR Headset"
}
responsebody -
{
    "error": false,
    "message": null,
    "data": {
        "productId": "1005",
        "price": 144.0,
        "color": "pink",
        "productName": "Tronz Wireless VR Headset",
        "inventory": "155",
        "properties": {
            "category": "Electronic Gadget",
            "connectionType": "Wireless"
        }
    }
}
<b>Example - 2</b>
requestbody -
{
    "productId": "1005",
    "updateKey": "color",
    "updateValue": "White"
}
responsebody -
{
    "error": false,
    "message": null,
    "data": {
        "productId": "1005",
        "price": 144.0,
        "color": "White",
        "productName": "Tronz Wireless VR Headset",
        "inventory": "155",
        "properties": {
            "category": "Electronic Gadget",
            "connectionType": "Wireless"
        }
    }
}
</pre>

<h5><u>Delete a Product By Primary Key Id:</u> </h5>
<p><u>Api Details:</u> Delete a Product Item By Primary Key Id from the DynamoDB database table.</p>
URL endpoint - {hostname}/{stage}/products/deleteproductbyid<br />
http method - "DELETE"<br />
produces "text"
<pre>
requestbody -
{
    "productId": "1006"
}
responsebody -
{
    "error": false,
    "message": "Product deleted successfully!",
    "data": null
}
</pre>