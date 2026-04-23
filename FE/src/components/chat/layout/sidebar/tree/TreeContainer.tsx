import { Container, ContainerProps } from "@chakra-ui/react";

type TreeContainerProps = ContainerProps

export default function SidebarTreeContainer(props: TreeContainerProps) {
  return (
    <Container
      bg={"bg.panel"}
      h={"full"}
      rounded={2}
      p={2}
      {...props}
    />
  )
}
