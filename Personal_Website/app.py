from flask import Flask, redirect, url_for, request, session, render_template
import Profile_Generation as PG

app = Flask(__name__)


@app.route('/')
def homepage():
	return render_template('index.html')

@app.route('/loading', methods=['POST'])
def loading():
	if request.method == 'POST':
		prof_desc = request.form['nm']
		session['prof_desc'] = prof_desc
		return render_template('loading.html')

@app.route('/create_profile')
def create_profile():
	prof_desc = session['prof_desc']
	profile_generator = PG.Profile_Generator()
	similair_artist = profile_generator.get_similair_artist(prof_desc)
	session['similair_artist'] = similair_artist
	image_status = profile_generator.get_image(prof_desc, similair_artist)
	if image_status  != 'success':
		profile_generator.get_image("a toucan wearing a hat", "vermeer")
	session['image_status'] = image_status
	return 'image creation done'

@app.route('/landing')
def landing():
	image_status = session['image_status']
	prof_desc = session['prof_desc']
	similair_artist = session['similair_artist']
	return render_template('landing.html', sentence = prof_desc, similair_artist = similair_artist, image_status = image_status)

@app.route('/about_me')
def about_me():
	return render_template('aboutme.html')

@app.route('/reading')
def reading():
	return render_template('reading.html')

@app.route('/chore_bot')
def chore_bot():
	return render_template('chorebot.html') 


if __name__ == '__main__':
	app.secret_key = 'mysecretkey'
	app.run(debug=True)