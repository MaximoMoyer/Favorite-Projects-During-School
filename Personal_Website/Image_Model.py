import os
import requests
import pdb
import base64


def produce_image(prompt,sess_id):
    #to delete
    os.environ["API_HOST"] = "https://api.stability.ai"
    api_host = os.getenv('API_HOST')
    url = f'{api_host}/v1/user/account'
    #to delete
    os.environ["STABILITY_API_KEY"] = ""
    api_key = os.getenv("STABILITY_API_KEY")

    engine_id = "stable-diffusion-v1-5"

    response = requests.post(
        f"{api_host}/v1/generation/{engine_id}/text-to-image",
        headers={
            "Content-Type": "application/json",
            "Accept": "application/json",
            "Authorization": f"Bearer {api_key}"
        },
        json={
            "text_prompts": [
                {
                    "text": f"{prompt}"
                }
            ],
            "cfg_scale": 7,
            "clip_guidance_preset": "FAST_BLUE",
            "height": 512,
            "width": 512,
            "samples": 1,
            "steps": 30,
        },
    )
    print(response)
    data = response.json()
    if response.status_code == 400:
        if data["message"] == 'Invalid prompts detected':
            return 'Invalid prompt, please try, more appropriately, again.'
        else:
            return "Something went wrong, we aren't quite sure why, please try again."
    else:
        image = data["artifacts"][0]["base64"]
        if not os.path.exists(f"./static/{sess_id}/images"):
            os.makedirs(f"./static/{sess_id}/images")
        with open(f"./static/{sess_id}/images/profile_image.png", "wb") as f:
            f.write(base64.b64decode(image))
        return 'success'

