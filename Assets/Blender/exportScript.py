import bpy
import os

from math import pi

# Ensure all folders of the path exist
#path = "C:/Blender_Export/"
path = "C:/Users/Hampus/Desktop/1337xXSlayer/Project/core/assets/map/"
fileName = "BasicMap.txt"
os.makedirs(path, exist_ok=True)

#Store lux elements in these dicts. Luxelements contains list of objects
luxElements = {}
luxContainers = {}

def Eu_degr(eu): # eu in radians
    degx=((eu[0]/(pi+pi))*360.0)
    degy=((eu[1]/(pi+pi))*360.0)
    degz=((eu[2]/(pi+pi))*360.0)
    eu_degr = [degx, degy, degz]

    return eu_degr

#Get tag string from element
def getTag(obj):
    if obj.type in 'CAMERA':
        return "Camera"
    if obj.type in 'MESH':
        #IMPORTANT!
        #Ob.library returns null if object is not linked
        if ob.library:
            return None
        else:
            if "tag" in obj.keys():
                return obj.get("tag")
            return "Mesh"
    if obj.type in 'LAMP':
        return "Light"
    #If not found return nothing
    return None

# Write data out to file
with open(path + fileName, "w") as file:
    print("-----------------Start---------------------")
    file.write("<?xml version=\"1.0\"?>\n<Map>\n")

    #Iterate through all objects in blend file
    for ob in bpy.data.objects:
        values = "\t"
        tag = getTag(ob)
        if tag:
            values += "<" + tag + " "

            #NOTE TODO z and y has changed values
            rotation = Eu_degr(ob.rotation_euler)
            position = "x=\"%f\" y=\"%f\" z=\"%f\" rotationX=\"%f\" rotationY=\"%f\" rotationZ=\"%f\"" % (ob.location.x, ob.location.z, ob.location.y, rotation[0], rotation[1], rotation[2])
            values += position

            scale = " scaleX=\"%f\" scaleY=\"%f\" scaleZ=\"%f\"" % (ob.scale.x, ob.scale.y, ob.scale.z)
            values += scale


            #Iterate thorugh all custom properties of object
            for key in ob.keys():

                if key not in ['_RNA_UI']:
                    values += (" " + key + "=\"" + str(ob[key]) + "\"")


            #If object is linked
            if ob.library:
                print("Found a linked object")

            file.write(values + "></" + tag + ">\n")
    file.write("</Map>")
    print("-----------------Done---------------------")
