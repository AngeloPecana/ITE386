from flask import Flask, request

app = Flask(__name__)

# Diabetic code by Dr. Fuentes Feb, 2024

@app.route('/', methods=['GET', 'POST'])
def diabetes_check():
    if request.method == 'POST':
        name = request.form['name']
        age = int(request.form['age'])
        address = request.form['address']
        illness_history = request.form['illness_history']
        blood_sugar = int(request.form['blood_sugar'])

        result = check_diabetes(blood_sugar)

        return f"""
        <!DOCTYPE html>
        <html>
        <head>
            <title>Diabetes Check Result</title>
        </head>
        <body>
            <h1>Diabetes Check Result</h1>
            <p>Name: {name}</p>
            <p>Age: {age}</p>
            <p>Address: {address}</p>
            <p>Illness History: {illness_history}</p>
            <p>Blood Sugar Level: {blood_sugar}</p>
            <p>Result: {result}</p>
            <br>
            <a href="/">Check Again</a>
        </body>
        </html>
        """

    return """
    <!DOCTYPE html>
    <html>
    <head>
        <title>Diabetes Check</title>
    </head>
    <body>
        <h1>Diabetes Check</h1>
        <form method="POST" action="/">
            <label for="name">Name: </label>
            <input type="text" id="name" name="name" required><br>

            <label for="age">Age:</label>
            <input type="number" id="age" name="age" required><br>

            <label for="address">Address:</label>
            <input type="text" id="address" name="address" required><br>

            <label for="illness_history">Illness History: </label>
            <textarea id="illness_history" name="illness_history" required></textarea><br>

            <label for="blood_sugar">Blood Sugar Level:</label>
            <input type="number" id="blood_sugar" name="blood_sugar" required><br>

            <input type="submit" value="Check">
        </form>
    </body>
    </html>
    """

def check_diabetes(blood_sugar):
    if blood_sugar >= 126:
        return "Diabetic"
    else:
        return "Not Diabetic"

if __name__ == '__main__':
    app.run(debug=True)
