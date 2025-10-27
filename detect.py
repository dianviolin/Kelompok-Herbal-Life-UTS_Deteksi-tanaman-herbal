from ultralytics import YOLO
import cv2

model = YOLO("models/yolo_herbal/weights/best.pt")
source = "dataset/images/img1.jpg"
results = model.predict(source)

for result in results:
    img = result.plot()
    cv2.imshow("Detection", img)
    cv2.waitKey(0)
cv2.destroyAllWindows()
